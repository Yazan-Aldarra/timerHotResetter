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
- Circular clock ring around the digits, with tick marks. The player-colored arc depletes like a clock and fades as time runs down.
- Audible ticking each beat, generated with the Web Audio API (no asset files). Tempo is ratioed to the round: it holds at 1000ms for the first half, then accelerates smoothly to 150ms, so short rounds do not start frantic.
- A sound toggle in the top bar mutes/unmutes ticking. Audio unlocks on the first click/keypress and starts ticking immediately once unlocked (browser autoplay rules).
- Low-time state: last 10 seconds switch digits/ring to red and pulse gently.
- Roster strip of player pills, with the active player highlighted; keeps everyone oriented.
- **Player colors:** a curated 8-color palette (cyan, amber, violet, pink, blue, green, orange, lime) is assigned by player order and cycles after 8. Each roster pill shows its color; the active pill is solid with dark text.
- The timer card adopts the current player's color across its whole surface: background tint, border, aura, player name, clock ring, ripple/flash, focus ring, and hint dot. Colors change during the name-swap blur, with a short transition.
- Low-time red overrides the clock digits, ring, border, and aura only; the player name, card background, and roster keep their assigned color.
- Toggle button becomes a pill with play/pause icon and a label that changes with state ("Pause timer" / "Resume timer").
- The whole timer card is a skip target: clicking/tapping it advances to the next player and resets the countdown to the full round time. It is also keyboard-activatable with Enter/Space.
- Skip feedback: a ripple from the click point, a brief glow flash on the card, and a blur/slide "swap" animation on the player name. Animations are skipped under `prefers-reduced-motion`.
- `aria-live="polite"` on the player name; the timer uses `role="timer"` with `aria-live="off"` so the countdown does not spam screen readers.
- Existing Thymeleaf bindings (`th:text`, `th:each`) and timer logic stay intact.

## Implementation Plan

1. Add an inline `<style>` block to each template. Static files are not served by the single Jersey servlet, so inline CSS avoids touching `JettyServer` or adding a static route.
2. Keep a shared set of CSS custom properties (`--bg`, `--surface`, `--text`, `--muted`, `--accent`, etc.) copied into both files for a consistent look.
3. Rework markup only enough to add semantic wrappers, helper text, and icon SVGs. Preserve every hook the JS depends on (`#players_list`, `#template_player_list_item`, `#add_user`, `#timer`, `#users`, `#current_user`, `timeInSec`, `name`).
4. Make small, contained JS additions in `Timer.html`:
   - update toggle button label/icon,
   - update the circular clock ring's stroke offset and fade each tick,
   - add/remove an `.is-low` class at 10 seconds,
   - highlight the active roster pill in `nextUser()`,
   - assign palette colors to players by index and set `--player-color` on the card and roster pills in `setCurrentUser()`,
   - wire card click + Enter/Space to reset the countdown and advance to the next player, spawn a ripple at the pointer, and run a two-phase name swap with the Web Animations API (change the player and color between outgoing and incoming frames),
   - schedule Web Audio ticks with a duration-relative delay (1000ms until halfway, easing down to 150ms), gated by a mute toggle; once audio unlocks, restart the tick schedule so sound begins immediately.
5. Responsive at mobile widths: stacked form fields, smaller timer text, wrapping roster.

## Non-Goals

- No new libraries, CSS frameworks, build tooling, or external assets.
- No changes to routes, controller logic, or data flow.
- No new features beyond the timer-state polish and player-color theming listed above.
