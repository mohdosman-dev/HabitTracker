# Habit Tracker App

## Purpose

A native Android habit tracker app built as the project for a YouTube coding course demonstrating Claude Code-assisted development. The app is the vehicle — Claude Code is the star. Scope: 3 screens, dark theme, purely local data.

## What Is a Habit

A habit is a recurring activity the user wants to track daily. Each habit has:
- A **name** (max 50 characters, e.g., "Morning Run", "Read 30min")
- An **icon** chosen from a predefined set of 20 icons
- A **frequency** — which days of the week it should be performed (e.g., weekdays only, every day, specific days)
- A **creation date** — streaks only count from this date forward

A habit is either **done** or **not done** on any given day. It can only be completed once per day. Duplicate habit names are allowed.

### Streaks

- **Current streak**: the number of consecutive scheduled days the habit has been completed, counting backward from today (or yesterday if today isn't completed yet). Only days the habit is scheduled for count.
- **Best streak**: the longest consecutive completion streak ever recorded for a habit.

### Completion

Completing a habit for today creates a record. Uncompleting removes it. A habit can only have one completion per day.
