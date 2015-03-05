/**
 * Copyright (C) 2014 Embedded Systems and Pervasive Computing Lab - UFCG
 * All rights reserved.
 */

package slidermenu;

/**
 * The Class NavDrawerItem.
 */
public class NavDrawerItem {

    /** The title. */
    private String mTitle;

    /** The icon. */
    private int mIcon;

    /** The count. */
    private String count = "0";
    // boolean to set visiblity of the counter
    /** The is counter visible. */
    private boolean isCounterVisible = false;

    /**
     * Instantiates a new nav drawer item.
     */
    public NavDrawerItem() {
    }

    /**
     * Instantiates a new nav drawer item.
     *
     * @param title
     *            the title
     * @param icon
     *            the icon
     */
    public NavDrawerItem(final String title, final int icon) {
        mTitle = title;
        mIcon = icon;
    }

    /**
     * Instantiates a new nav drawer item.
     *
     * @param title
     *            the title
     * @param icon
     *            the icon
     * @param isVisible
     *            the is counter visible
     * @param newCount
     *            the count
     */
    public NavDrawerItem(final String title, final int icon,
            final boolean isVisible, final String newCount) {
        mTitle = title;
        mIcon = icon;
        isCounterVisible = isVisible;
        count = newCount;
    }

    /**
     * Gets the title.
     *
     * @return the title
     */
    public final String getTitle() {
        return mTitle;
    }

    /**
     * Gets the icon.
     *
     * @return the icon
     */
    public final int getIcon() {
        return mIcon;
    }

    /**
     * Gets the count.
     *
     * @return the count
     */
    public final String getCount() {
        return count;
    }

    /**
     * Gets the counter visibility.
     *
     * @return the counter visibility
     */
    public final boolean getCounterVisibility() {
        return isCounterVisible;
    }

    /**
     * Sets the title.
     *
     * @param title
     *            the new title
     */
    public final void setTitle(final String title) {
        mTitle = title;
    }

    /**
     * Sets the icon.
     *
     * @param icon
     *            the new icon
     */
    public final void setIcon(final int icon) {
        mIcon = icon;
    }

    /**
     * Sets the count.
     *
     * @param newCount
     *            the new count
     */
    public final void setCount(final String newCount) {
        count = newCount;
    }

    /**
     * Sets the counter visibility.
     *
     * @param isVisible
     *            the new counter visibility
     */
    public final void setCounterVisibility(final boolean isVisible) {
        this.isCounterVisible = isVisible;
    }
}
