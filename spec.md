# UI Refresh Specification

## Goal

Make the two existing pages (`Home.html`, `Timer.html`) feel like a polished game-night tool: calm, readable, and usable at a glance from across a table. No backend or framework changes.

## Design Direction

Dark, focused "tabletop" theme:

- **Palette:** deep charcoal/navy background with a soft radial glow, raised surfaces, muted grey text, warm amber accent for the timer, cyan secondary accent for actions.
- **Type:** system font stack (`Segoe UI`, `Inter`, `system-ui`, sans-serif). No external fonts or CDNs, so the app works offline.
- **Shape:** 16-24px rounded cards, 1px translucent borders, soft shadows, generous spacing.
- **Motion:** subtle fades and a gentle pulse when time runs low. All motion disabled under `prefers-reduced-motion`.
- **Icons:** small inline SVGs only (plus, play/pause, clock). No icon libraries.

## Home Page

- Centered page header with app name and one-line tagline.
- Single card: "Set up your game".
- Player rows become numbered chips: index badge + text input + subtle focus ring. Row layout stays responsive.
- "Add player" becomes a dashed ghost button with a plus icon.
- Time field gets a unit hint ("seconds") and a short helper line.
- Primary "Start" button is full-width, amber, with hover/active states.
- Existing hidden template row, IDs, `name` attributes, and form action are preserved so the current JS keeps working.

## Timer Page

- Full-viewport centered layout, no scrolling.
- Top bar with brand mark and a "Now playing" label.
- Current player name displayed large and bold.
- Timer digits use `clamp()` sizing and tabular numerals so they do not jump while counting.
- Slim progress bar under the digits (CSS variable updated by JS).
- Low-time state: last 10 seconds switch digits/bar to red and pulse gently.
- Roster strip of player pills, with the active player highlighted; keeps everyone oriented.
- Toggle button becomes a pill with play/pause icon and a label that changes with state ("Pause timer" / "Resume timer").
- `aria-live="polite"` on player name and timer for screen readers.
- Existing Thymeleaf bindings (`th:text`, `th:each`) and timer logic stay intact.

## Implementation Plan

1. Add an inline `<style>` block to each template. Static files are not served by the single Jersey servlet, so inline CSS avoids touching `JettyServer` or adding a static route.
2. Keep a shared set of CSS custom properties (`--bg`, `--surface`, `--text`, `--muted`, `--accent`, etc.) copied into both files for a consistent look.
3. Rework markup only enough to add semantic wrappers, helper text, and icon SVGs. Preserve every hook the JS depends on (`#players_list`, `#template_player_list_item`, `#add_user`, `#timer`, `#users`, `#current_user`, `timeInSec`, `name`).
4. Make small, contained JS additions in `Timer.html`:
   - update toggle button label/icon,
   - set the progress CSS variable each tick,
   - add/remove an `.is-low` class at 10 seconds,
   - highlight the active roster pill in `nextUser()`.
5. Responsive at mobile widths: stacked form fields, smaller timer text, wrapping roster.

## Non-Goals

- No new libraries, CSS frameworks, build tooling, or external assets.
- No changes to routes, controller logic, or data flow.
- No new features beyond the small timer-state polish listed above.
