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
    if (fragment.length == 1) return []
    new CommandParser(commandFragment).parse()
  }

  DocumentFragment getCommandFragment() {
    int x = matchesOldForm(fragment) ? 0 : 2
    if (bracketed) {
      fragment.createFragment([x:x, y:2], fragment.length - 3) // chop off [!], {, }
    } else {
      fragment.createFragment([x:x, y:1], fragment.length - 1) // chop off [!] or ?
    }
  }

  private boolean isBracketed() {
    return fragment.lines[1] == "{"
  }

  static boolean matches(DocumentFragment fragment) {
    matchesOldForm(fragment) || matchesNewForm(fragment)
  }

  private static def matchesOldForm(DocumentFragment fragment) {
    fragment.lines.first().startsWith("[!]")
  }

  private static def matchesNewForm(DocumentFragment fragment) {
    fragment.lines.first().startsWith("? ")
  }

  static int numMatchingLines(DocumentFragment fragment) {
    if (matchesOldForm(fragment)) return numMatchingLinesOldForm(fragment)
    if (matchesNewForm(fragment)) return numMatchingLinesNewForm(fragment)
    0
  }

  static int numMatchingLinesNewForm(DocumentFragment fragment) {
    int size = fragment.lines.size()
    for (int i = 1; i < size; i++) {
      if (!fragment.lines[i].startsWith("  ")) return i
    }
    return size
  }

  private static int numMatchingLinesOldForm(DocumentFragment fragment) {
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
    if (matchesNewForm(fragment)) commandLines.each { it.text = "  ${it.text}" }
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

  @Override
  List<String> toNewStyle() {
    def lines = ["? ${requirement}"]
    commands.collect { it.toNewStyle() }.flatten().each { lines << "  $it" }
    lines
  }

  @Override
  List<String> toOldStyle() {
    def lines = ["[!]${requirement}"]
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

  String getRequirement() {
    matchesNewForm(fragment) ? fragment.lines.first().substring(2) : fragment.lines.first().substring(3)
  }

}
