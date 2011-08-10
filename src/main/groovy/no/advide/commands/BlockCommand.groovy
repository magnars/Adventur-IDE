package no.advide.commands

import java.awt.Color
import no.advide.DocumentFragment
import no.advide.Fix
import no.advide.FormattedLine
import no.advide.RoomNumber

abstract class BlockCommand extends Command {

  static int numMatchingLinesNewForm(DocumentFragment fragment) {
    int size = fragment.lines.size()
    for (int i = 1; i < size; i++) {
      if (!fragment.lines[i].startsWith("  ")) return i
    }
    return size
  }

  static int numMatchingLinesOldForm(DocumentFragment fragment) {
    int size = fragment.lines.size()
    int numBlocks = 0
    for (int i = 1; i < size; i++) {
      if (fragment.lines[i] == "{") numBlocks++
      if (fragment.lines[i] == "}") numBlocks--
      if (numBlocks == 0) return i + 1
    }
    return size
  }

  CommandList commands

  BlockCommand(fragment) {
    super(fragment)
    commands = parseCommands()
  }

  CommandList parseCommands() {
    if (fragment.length == 1) return []
    new CommandParser(commandFragment).parse()
  }

  DocumentFragment getCommandFragment() {
    int x = isOldForm() ? 0 : 2
    if (bracketed) {
      fragment.createFragment([x:x, y:2], fragment.length - 3) // chop off [!], {, }
    } else {
      fragment.createFragment([x:x, y:1], fragment.length - 1) // chop off [!] or ?
    }
  }

  boolean isBracketed() {
    return fragment.lines[1] == "{"
  }

  abstract boolean isOldForm()
  abstract boolean isNewForm()

  @Override
  List<Fix> getFixes() {
    myFixes() + subcommandFixes()
  }

  private def subcommandFixes() {
    (List<Fix>) commands.collect { it.fixes }.flatten()
  }

  private def myFixes() {
    newForm ? [] : [new Fix(fragment.offset.y, { replaceWithNewStyle() })]
  }

  @Override
  List<FormattedLine> getFormattedLines() {
    def lines = [new FormattedLine(text: fragment.lines[0], color: color)]
    lines = addFormattedLinesForCommands(lines)
    if (bracketed) addBrackets(lines)
    if (fragment.cursor) emboss(lines)
    (List<FormattedLine>) lines
  }

  private def addBrackets(lines) {
    lines.add(1, new FormattedLine(text: "{", color: Color.gray))
    if (fragment.lines.last() == "}") lines << new FormattedLine(text: "}", color: Color.gray)
  }

  private def addFormattedLinesForCommands(lines) {
    def commandLines = commands.collect { it.formattedLines }.flatten()
    if (isNewForm()) commandLines.each { it.text = "  ${it.text}" }
    lines << commandLines
    lines.flatten()
  }

  private def emboss(lines) {
    lines.first().isEmbossedTop = true
    lines.each { it.isEmbossed = true }
    lines.last().isEmbossedBottom = true
  }

  @Override
  List<RoomNumber> getRoomNumbers() {
    (List<RoomNumber>) commands.collect { it.roomNumbers }.flatten()
  }

  @Override
  void justifyProse(int width) {
    commands.each { it.justifyProse(width - 2) }
  }

  List<String> toNewStyle(firstLine) {
    def lines = [firstLine]
    commands.collect { it.toNewStyle() }.flatten().each { lines << "  $it" }
    lines
  }

  List<String> toOldStyle(firstLine) {
    def lines = [firstLine]
    if (commands.empty) {
      lines << ""
    } else {
      if (oldStyleNeedsBrackets()) lines << "{"
      commands.collect { it.toOldStyle() }.flatten().each { lines << it }
      if (oldStyleNeedsBrackets()) lines << "}"
    }
    lines
  }

  private boolean oldStyleNeedsBrackets() {
    return commands.size() > 1 || (commands.size() > 0 && commands.first().toOldStyle().size() > 1)
  }

}
