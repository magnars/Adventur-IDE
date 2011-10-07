package no.advide.commands

import no.advide.Document
import no.advide.DocumentFragment

class CommandParser {

  static List commandTypes = [
      AlternativeCommand,
      ConditionalCommand,
      ContinueCommand,
      ProseCommand,
      ReinstateAlternativeCommand,
      RemoveAlternativeCommand,
      UnknownCommand
  ]

  List<String> strings
  CommandList commands
  DocumentFragment document
  int index

  CommandParser(Document doc) {
    this(doc.createFragment([x:0, y:0], doc.lines.size()))
  }

  CommandParser(DocumentFragment doc) {
    document = doc
    strings = document.lines
    commands = new CommandList()
    index = 0
    if (fragmentTypeCache.size() > 100000) { fragmentTypeCache = [:] } // don't grow into the heavens
  }

  CommandList parse() {
    while (index < strings.size()) {
      commands << findMatchingCommand()
    }
    return commands
  }

  def static fragmentTypeCache = [:]

  private Command findMatchingCommand() {
    def fragment = document.createFragment([x:0, y:index], strings.size() - index)
    def type = findCommandType(fragment)
    return createCommand(type, fragment)
  }

  private def findCommandType(DocumentFragment fragment) {
    def lines = fragment.lines
    def cacheHit = fragmentTypeCache[lines]
    if (cacheHit) {
      return cacheHit
    }
    def type = scanForType(fragment)
    fragmentTypeCache[lines] = type
    return type
  }

  private def scanForType(DocumentFragment fragment) {
    for (type in commandTypes) {
      if (type.matches(fragment)) {
        return type
      }
    }
    throw new IllegalStateException("no matching commands")
  }

  private Command createCommand(type, fragment) {
    int numLines = type.numMatchingLines(fragment)
    fragment.length = numLines
    Command command = type.newInstance(fragment)
    index += numLines
    return command
  }

}
