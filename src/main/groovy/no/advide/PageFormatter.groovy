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
    lines
  }

  def formatLines(List<FormattedLine> lines) {
    colorNonexistantRoomNumbers(lines)
    if (!modified) highlightTargetRoomNumber(lines)
  }

  void colorNonexistantRoomNumbers(List<FormattedLine> lines) {
    commands.roomNumbers.each { RoomNumber r ->
      if (!r.exists()) lines[r.position.y].formatSubstring(r.position.x, r.length, Color.red)
    }
  }

  void highlightTargetRoomNumber(List<FormattedLine> lines) {
    def r = page.targetRoomNumber
    if (r) lines[r.position.y].highlightSubstring(r.position.x, r.length, new Color(0, 0, 150, 20))
  }

}
