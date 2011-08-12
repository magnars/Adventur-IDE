package no.advide

import java.awt.Color
import no.advide.commands.CommandList

class PageFormatter {

  Page page
  boolean modified

  PageFormatter(Page page, boolean isModified) {
    this.page = page
    this.modified = isModified
  }

  CommandList getCommands() {
    page.commands
  }

  List<FormattedLine> getFormattedLines() {
    def lines = commands.formattedLines
    formatLines(lines)
    addFixIcons(lines)
    lines
  }

  void addFixIcons(List<FormattedLine> lines) {
    page.fixes.each { lines[it.line].icon = it.icon }
    if (page.nextFix) { lines[page.nextFix.line].icon = "${page.nextFix.icon}_active" }
  }

  def formatLines(List<FormattedLine> lines) {
    colorRoomNumbers(lines)
    if (!modified) highlightTargetRoomNumber(lines)
  }

  void colorRoomNumbers(List<FormattedLine> lines) {
    commands.roomNumbers.each { RoomNumber r ->
      lines[r.position.y].formatSubstring(r.position.x, r.length, r.exists() ? new Color(60, 60, 200) : Color.red)
    }
  }

  void highlightTargetRoomNumber(List<FormattedLine> lines) {
    def r = page.targetRoomNumber
    if (r) lines[r.position.y].highlightSubstring(r.position.x, r.length, new Color(0, 0, 150, 20))
  }

}
