package no.advide

import java.awt.Color
import no.advide.commands.CommandList

class PageFormatter {

  Page page

  PageFormatter(Page page) {
    this.page = page
  }

  CommandList getCommands() {
    page.commands
  }

  List<FormattedLine> getFormattedLines() {
    def lines = commands.formattedLines
    formatRoomNumbers(lines)
    lines
  }

  void formatRoomNumbers(List<FormattedLine> lines) {
    commands.roomNumbers.each { RoomNumber r ->
      if (!r.exists()) colorRed(r, lines[r.position.y])
    }
  }

  private def colorRed(RoomNumber r, line) {
    line.formatSubstring(r.position.x, r.number.toString().length(), Color.red)
  }

}
