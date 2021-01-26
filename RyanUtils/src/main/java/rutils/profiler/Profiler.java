package rutils.profiler;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import rutils.Logger;
import rutils.group.Pair;

import java.util.*;
import java.util.function.Function;

import static rutils.NumUtil.round;

public class Profiler
{
    private static final Logger LOGGER = new Logger();
    
    private static final HashMap<String, Profiler> CACHE = new HashMap<>();
    
    /**
     * Gets a profiler associated with the inputted name.
     *
     * @param name The name of the profiler.
     * @return The new/existing profiler associated with the given name.
     */
    public static Profiler get(String name)
    {
        return Profiler.CACHE.computeIfAbsent(name, Profiler::new);
    }
    
    private final String name;
    
    private boolean enabled, newEnabled;
    
    private long warningThreshold = 100_000_000L;
    
    private boolean started;
    
    private final Stack<Pair<String, Long>>        sections         = new Stack<>();
    private final ArrayList<Long>                  frameTimeList    = new ArrayList<>();
    private final HashMap<String, ArrayList<Long>> sectionsTimeList = new HashMap<>();
    
    protected final Section nullSection = new Section.NullSection(this);
    
    private Profiler(String name)
    {
        this.name = name;
    }
    
    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Profiler profiler = (Profiler) o;
        return this.name.equals(profiler.name);
    }
    
    @Override
    public int hashCode()
    {
        return Objects.hash(this.name);
    }
    
    @Override
    public String toString()
    {
        return "Profiler{" + '\'' + this.name + '\'' + '}';
    }
    
    public String name()
    {
        return this.name;
    }
    
    /**
     * @return If the profiler is enabled.
     */
    public boolean enabled()
    {
        return this.enabled;
    }
    
    /**
     * Sets the profiler to enabled/disabled.
     *
     * @param enabled The new enabled state.
     */
    public void enabled(boolean enabled)
    {
        Profiler.LOGGER.finest("Setting enabled flag for %s to %s", this, enabled);
        
        this.newEnabled = enabled;
        
        if (!this.started) this.enabled = enabled;
    }
    
    /**
     * Toggles the enabled state.
     */
    public void toggleEnabled()
    {
        enabled(!this.enabled);
    }
    
    /**
     * Enables the profiler.
     */
    public void enable()
    {
        enabled(true);
    }
    
    /**
     * Disables the profiler.
     */
    public void disable()
    {
        enabled(false);
    }
    
    /**
     * @return Return this time in seconds before the profiler will log a warning message.
     */
    public double warningThreshold()
    {
        return (double) this.warningThreshold / 1_000_000_000D;
    }
    
    /**
     * Sets the time in second before the profiler will log a warning message.
     *
     * @param warningThreshold The time in seconds.
     */
    public void warningThreshold(double warningThreshold)
    {
        Profiler.LOGGER.finest("Setting warning threshold for %s to %s", this, warningThreshold);
        
        this.warningThreshold = (long) (warningThreshold * 1_000_000_000D);
    }
    
    /**
     * Clears the frame data.
     */
    public void clear()
    {
        Profiler.LOGGER.finest("Clearing frame data for", this);
        
        this.frameTimeList.clear();
        this.sectionsTimeList.clear();
    }
    
    /**
     * Starts the profiler frame. The profiler must be enabled and the frame must be stopped.
     */
    public void startFrame()
    {
        if (this.enabled = this.newEnabled)
        {
            if (this.started)
            {
                Profiler.LOGGER.warning("Frame for %s already started.", this);
            }
            else
            {
                Profiler.LOGGER.finest("Starting Frame for", this);
                
                this.sections.clear();
                
                this.frameTimeList.add(System.nanoTime());
                
                this.started = true;
            }
        }
    }
    
    /**
     * Ends the profiler frame. The profiler must be enabled and the frame must have been started.
     */
    public void endFrame()
    {
        if (this.enabled)
        {
            if (!this.started)
            {
                Profiler.LOGGER.warning("Frame for %s was never started.", this);
            }
            else
            {
                Profiler.LOGGER.finest("Ending Frame for", this);
                
                this.frameTimeList.add(System.nanoTime() - this.frameTimeList.remove(this.frameTimeList.size() - 1));
                
                this.started = false;
                if (!this.sections.isEmpty()) Profiler.LOGGER.warning("Frame for %s ended before all sections were ended (remainder: '%s')", this, this.sections.peek());
            }
        }
    }
    
    /**
     * Begins a section to start timing it. Must be paired with {@link #endSection()}. You can call again with a unique name to start a sub section.
     *
     * @param name The section name.
     * @return The new section.
     */
    public @NotNull Section startSection(@NotNull String name)
    {
        if (this.enabled)
        {
            if (!this.started)
            {
                Profiler.LOGGER.warning("Cannot start section '%s' in %s because frame wasn't started.", name, this);
            }
            else
            {
                String parent  = currentSection();
                String section = parent != null ? parent + '.' + name : name;
                
                Profiler.LOGGER.finest("Starting %s in", section, this);
                
                this.sections.push(new Pair<>(section, System.nanoTime()));
                
                return new Section(this, section);
            }
        }
        return this.nullSection;
    }
    
    /**
     * Ends a section and records the time since {@link #startSection} was called. Must be paired with {@link #startSection}.
     */
    public void endSection()
    {
        if (this.enabled)
        {
            if (!this.started)
            {
                Profiler.LOGGER.warning("Cannot stop section because profiler frame wasn't started.");
            }
            else if (this.sections.isEmpty())
            {
                Profiler.LOGGER.warning("No section was started.");
            }
            else
            {
                Pair<String, Long> data = this.sections.pop();
                
                Profiler.LOGGER.finest("Ending ", data.a);
                
                long sectionTime = System.nanoTime() - data.b;
                this.sectionsTimeList.computeIfAbsent(data.a, s -> new ArrayList<>()).add(sectionTime);
                
                if (sectionTime > this.warningThreshold) Profiler.LOGGER.warning("'%s' took approx %.3f us", data.a, sectionTime / 1_000D);
            }
        }
    }
    
    /**
     * @return The current section or null if one hasn't been created.
     */
    public @Nullable String currentSection()
    {
        return !this.sections.isEmpty() ? this.sections.peek().a : null;
    }
    
    /**
     * Gets a multiline string that shows the average, minimum, and maximum for each section over the number of frames profiled.
     * <p>
     * All child sections will be included in the string.
     *
     * @param parent The parent section to collect or null for the entire data set.
     * @return The multiline string or null if disabled.
     */
    public @Nullable String getAvgDataString(@Nullable String parent)
    {
        if (this.enabled)
        {
            StringBuilder sb = new StringBuilder(this.name).append(" - Average Frame Data - ");
            format(0, parent, sb, true, this::getAverageData);
            sb.setLength(sb.length() - 1);
            return sb.toString();
        }
        return null;
    }
    
    /**
     * Gets a multiline string that shows the frame that took the minimum amount of time.
     * <p>
     * All child sections will be included in the string with the percentage that the child took in the parent
     * as well as the percentage of the frame time taken.
     *
     * @param parent The parent section to collect or null for the entire data set.
     * @return The multiline string or null if disabled.
     */
    public @Nullable String getMinDataString(@Nullable String parent)
    {
        if (this.enabled)
        {
            StringBuilder sb = new StringBuilder(this.name).append(" - Minimum Frame Data - ");
            format(0, parent, sb, true, this::getMinData);
            sb.setLength(sb.length() - 1);
            return sb.toString();
        }
        return null;
    }
    
    /**
     * Gets a multiline string that shows the frame that took the maximum amount of time.
     * <p>
     * All child sections will be included in the string with the percentage that the child took in the parent
     * as well as the percentage of the frame time taken.
     *
     * @param parent The parent section to collect or null for the entire data set.
     * @return The multiline string or null if disabled.
     */
    public @Nullable String getMaxDataString(@Nullable String parent)
    {
        if (this.enabled)
        {
            StringBuilder sb = new StringBuilder(this.name).append(" - Maximum Frame Data - ");
            format(0, parent, sb, true, this::getMaxData);
            sb.setLength(sb.length() - 1);
            return sb.toString();
        }
        return null;
    }
    
    private void format(int level, String parent, StringBuilder builder, boolean header, Function<String, List<? extends SectionData>> points)
    {
        List<? extends SectionData> apply = points.apply(parent);
        for (int i = header ? 0 : 1, n = apply.size(); i < n; i++)
        {
            SectionData point = apply.get(i);
            // builder.append(String.format("[%02d] ", level));
            builder.append("|   ".repeat(Math.max(0, level)));
            if (!point.name.equals(this.name)) builder.append(point.name.contains(".") ? point.name.substring(point.name.lastIndexOf(".") + 1) : point.name).append(" - ");
            builder.append(point.valueString()).append(System.lineSeparator());
            if (point.name.equals(parent) || point.name.equals(this.name))
            {
                level += 1;
            }
            else if (!point.name.contains("Unspecified"))
            {
                try
                {
                    format(level + 1, point.name, builder, false, points);
                }
                catch (Exception e)
                {
                    builder.append("[[ EXCEPTION ").append(e).append(" ]]");
                }
            }
        }
    }
    
    /**
     * Gets a ArrayList of data points that shows the average, minimum, and maximum for each section in the parent over the number of frames profiled.
     *
     * @param parent The parent section to collect or null for the top level.
     * @return The ArrayList of data points.
     */
    public @NotNull List<SectionData> getAverageData(@Nullable String parent)
    {
        if (!this.enabled) return new ArrayList<>();
        
        Function<String, Boolean> check = s -> (parent == null && !s.contains(".")) || (s.startsWith(parent + '.') && !s.replaceFirst(parent + '.', "").contains("."));
        
        ArrayList<SectionData> data = new ArrayList<>();
        for (String section : this.sectionsTimeList.keySet())
        {
            if (check.apply(section))
            {
                ArrayList<Long> times = this.sectionsTimeList.get(section);
                
                long minTime   = Long.MAX_VALUE;
                long maxTime   = Long.MIN_VALUE;
                long totalTime = 0;
                
                for (long time : times)
                {
                    minTime = Math.min(minTime, time);
                    maxTime = Math.max(maxTime, time);
                    totalTime += time;
                }
                
                data.add(new SectionData.Average(section, totalTime / times.size(), minTime, maxTime));
            }
        }
        
        long minTime   = Long.MAX_VALUE;
        long maxTime   = Long.MIN_VALUE;
        long totalTime = 0;
        
        for (long time : this.frameTimeList)
        {
            minTime = Math.min(minTime, time);
            maxTime = Math.max(maxTime, time);
            totalTime += time;
        }
        data.sort(Collections.reverseOrder());
        data.add(0, new SectionData.Average(parent == null ? this.name : parent, totalTime / this.frameTimeList.size(), minTime, maxTime));
        return data;
    }
    
    /**
     * Gets a ArrayList of data points that shows the frame that the parent took the minimum amount of time to execute.
     * </p>
     * The child sections will be included in the string with the percentage that the child took in the parent
     * as well as the percentage of the frame time taken.
     *
     * @param parent The parent section to collect or null for the top level.
     * @return The ArrayList of data points.
     */
    public @NotNull List<SectionData> getMinData(@Nullable String parent)
    {
        if (!this.enabled) return new ArrayList<>();
        
        ArrayList<Long> data = parent != null ? this.sectionsTimeList.get(parent) : this.frameTimeList;
        
        if (data == null) return new ArrayList<>();
        
        long min = Long.MAX_VALUE;
        int  idx = 0;
        for (int i = 0, n = data.size(); i < n; i++)
        {
            long value = data.get(i);
            if (value < min)
            {
                min = value;
                idx = i;
            }
        }
        return getFrameData(idx, parent);
    }
    
    /**
     * Gets a ArrayList of data points that shows the frame that the parent took the maximum amount of time to execute.
     * </p>
     * The child sections will be included in the string with the percentage that the child took in the parent
     * as well as the percentage of the frame time taken.
     *
     * @param parent The parent section to collect or null for the top level.
     * @return The ArrayList of data points.
     */
    public @NotNull List<SectionData> getMaxData(@Nullable String parent)
    {
        if (!this.enabled) return new ArrayList<>();
        
        ArrayList<Long> data = parent != null ? this.sectionsTimeList.get(parent) : this.frameTimeList;
        
        if (data == null) return new ArrayList<>();
        
        long max = Long.MIN_VALUE;
        int  idx = 0;
        for (int i = 0, n = data.size(); i < n; i++)
        {
            long value = data.get(i);
            if (value > max)
            {
                max = value;
                idx = i;
            }
        }
        return getFrameData(idx, parent);
    }
    
    private List<SectionData> getFrameData(int frame, String parent)
    {
        Function<String, Boolean> check = s -> (parent == null && !s.contains(".")) || (s.startsWith(parent + '.') && !s.replaceAll(parent + '.', "").contains("."));
        
        long actualTotal = 0;
        for (String section : this.sectionsTimeList.keySet()) if (check.apply(section)) actualTotal += this.sectionsTimeList.get(section).get(frame);
        
        long parentTotal = (parent != null ? this.sectionsTimeList.get(parent) : this.frameTimeList).get(frame);
        long globalTotal = Math.max(this.frameTimeList.get(frame), parentTotal);
        
        long total = Math.max(actualTotal, parentTotal);
        
        ArrayList<SectionData> data = new ArrayList<>();
        for (String section : this.sectionsTimeList.keySet())
        {
            if (check.apply(section))
            {
                long   time     = this.sectionsTimeList.get(section).get(frame);
                double percent  = round(((double) time / (double) total) * 100D, 3);
                double gPercent = round(((double) time / (double) globalTotal) * 100D, 3);
                data.add(new SectionData.Percent(section, time, percent, gPercent));
            }
        }
        
        if (parentTotal > actualTotal && !data.isEmpty())
        {
            long   time     = parentTotal - actualTotal;
            double percent  = round(((double) time / (double) total) * 100D, 3);
            double gPercent = round(((double) time / (double) globalTotal) * 100D, 3);
            data.add(new SectionData.Percent(parent != null ? parent + ".Unspecified" : "Unspecified", time, percent, gPercent));
        }
        
        data.sort(Collections.reverseOrder());
        data.add(0, new SectionData.Percent(parent == null ? this.name : parent, parentTotal, 100, round(parentTotal / (double) globalTotal * 100D, 3)));
        return data;
    }
}
