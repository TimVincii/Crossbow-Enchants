package me.timvinci.crossbowenchants.config;

// The config class of the mod.
public class ModConfig {
    private boolean enabled = true;
    private boolean flameEnabled = true;
    private boolean infinityEnabled = true;
    private boolean powerEnabled = true;
    private boolean punchEnabled = true;
    private boolean infinityAndMendingEnabled = true;
    private boolean piercingAndMultishotEnabled = true;
    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isFlameEnabled() {
        return flameEnabled;
    }

    public void setFlameEnabled(boolean flameEnabled) {
        this.flameEnabled = flameEnabled;
    }
    public boolean isInfinityEnabled() {
        return infinityEnabled;
    }
    public void setInfinityEnabled(boolean infinityEnabled) {
        this.infinityEnabled = infinityEnabled;
    }
    public boolean isPowerEnabled() {
        return powerEnabled;
    }
    public void setPowerEnabled(boolean powerEnabled) {
        this.powerEnabled = powerEnabled;
    }
    public boolean isPunchEnabled() {
        return punchEnabled;
    }
    public void setPunchEnabled(boolean punchEnabled) {
        this.punchEnabled = punchEnabled;
    }
    public boolean isInfinityAndMendingEnabled() {
        return infinityAndMendingEnabled;
    }
    public void setInfinityAndMendingEnabled(boolean infinityAndMendingEnabled) {
        this.infinityAndMendingEnabled = infinityAndMendingEnabled;
    }
    public boolean isPiercingAndMultishotEnabled() {
        return piercingAndMultishotEnabled;
    }
    public void setPiercingAndMultishotEnabled(boolean piercingAndMultishotEnabled) {
        this.piercingAndMultishotEnabled = piercingAndMultishotEnabled;
    }
}
