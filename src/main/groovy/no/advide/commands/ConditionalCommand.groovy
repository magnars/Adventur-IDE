package no.advide.commands

import java.awt.Color
import no.advide.DocumentFragment
import no.advide.FormattedLine
import no.advide.RoomNumber

class ConditionalCommand extends Command {

  CommandList commands

  ConditionalCommand(fragment) {
    super(fragment)
    commands = parseCommands()
  }

  CommandList parseCommands() {
    new CommandParser(commandFragment).parse()
  }

  DocumentFragment getCommandFragment() {
    if (bracketed) {
      fragment.createFragment([x:0, y:2], fragment.length - 3) // chop off [!], {, }
    } else {
      fragment.createFragment([x:0, y:1], 1) // chop off [!]
    }
  }

  private boolean isBracketed() {
    return fragment.length > 2
  }

  static boolean matches(DocumentFragment fragment) {
    fragment.lines.first().startsWith("[!]")
  }

  static int numMatchingLines(DocumentFragment fragment) {
    int size = fragment.lines.size()
    int numBlocks = 0
    for (int i = 1; i < size; i++) {
      if (fragment.lines[i] == "{") numBlocks++
      if (fragment.lines[i] == "}") numBlocks--
      if (numBlocks == 0) return i + 1
    }
    return size
  }

  @Override
  List<FormattedLine> getFormattedLines() {
    def lines = []
    lines << new FormattedLine(text: fragment.lines[0], color: color)
    if (bracketed) lines << new FormattedLine(text: fragment.lines[1], color: Color.gray)
    commands.each { c -> lines << c.formattedLines }
    if (bracketed) lines << new FormattedLine(text: fragment.lines.last(), color: Color.gray)
    lines = lines.flatten()
    if (fragment.cursor) emboss(lines)
    (List<FormattedLine>) lines
  }

  private def emboss(lines) {
    lines.first().isEmbossedTop = true
    lines.each { it.isEmbossed = true }
    lines.last().isEmbossedBottom = true
  }

  @Override
  List<RoomNumber> getRoomNumbers() {
    (List<RoomNumber>) commands.collect {c -> c.roomNumbers }.flatten()
  }

  @Override
  void optimizeDocument() {
    commands.each { c -> c.optimizeDocument() }
  }


}
