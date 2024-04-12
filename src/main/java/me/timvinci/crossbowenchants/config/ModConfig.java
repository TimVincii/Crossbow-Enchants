package me.timvinci.crossbowenchants.config;

// The config class of the mod.
public class ModConfig {
    private boolean Enabled = true;
    private boolean FlameEnabled = true;
    private boolean InfinityEnabled = true;
    private boolean PowerEnabled = true;
    private boolean PunchEnabled = true;
    private boolean InfinityAndMendingEnabled = true;
    private boolean PiercingAndMultishotEnabled = true;
    public boolean isEnabled() {
        return Enabled;
    }

    public void setEnabled(boolean Enabled) {
        this.Enabled = Enabled;
    }

    public boolean isFlameEnabled() {
        return FlameEnabled;
    }

    public void setFlameEnabled(boolean FlameEnabled) {
        this.FlameEnabled = FlameEnabled;
    }
    public boolean isInfinityEnabled() {
        return InfinityEnabled;
    }
    public void setInfinityEnabled(boolean InfinityEnabled) {
        this.InfinityEnabled = InfinityEnabled;
    }
    public boolean isPowerEnabled() {
        return PowerEnabled;
    }
    public void setPowerEnabled(boolean PowerEnabled) {
        this.PowerEnabled = PowerEnabled;
    }
    public boolean isPunchEnabled() {
        return PunchEnabled;
    }
    public void setPunchEnabled(boolean PunchEnabled) {
        this.PunchEnabled = PunchEnabled;
    }
    public boolean isInfinityAndMendingEnabled() {
        return InfinityAndMendingEnabled;
    }
    public void setInfinityAndMendingEnabled(boolean InfinityAndMendingEnabled) {
        this.InfinityAndMendingEnabled = InfinityAndMendingEnabled;
    }
    public boolean isPiercingAndMultishotEnabled() {
        return PiercingAndMultishotEnabled;
    }
    public void setPiercingAndMultishotEnabled(boolean PiercingAndMultishotEnabled) {
        this.PiercingAndMultishotEnabled = PiercingAndMultishotEnabled;
    }
}
