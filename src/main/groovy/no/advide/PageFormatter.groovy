package no.advide

import no.advide.commands.CommandList
import no.advide.ui.Theme

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
      lines[r.position.y].formatSubstring(r.position.x, r.length, r.exists() ? Theme.roomExists : Theme.roomDoesntExist)
    }
  }

  void highlightTargetRoomNumber(List<FormattedLine> lines) {
    def r = page.targetRoomNumber
    if (r) lines[r.position.y].highlightSubstring(r.position.x, r.length, Theme.roomHighlight)
  }

}
