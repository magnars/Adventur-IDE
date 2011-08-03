package no.advide

class EventEmitter {
  def callbacks = [:]

  def on(type, callback) {
    if (callbacks[type]) throw new UnsupportedOperationException("Only support for 1 callback at the moment")
    callbacks[type] = callback
  }

  def emit(type) {
    if (callbacks[type]) callbacks[type].call()
  }

  def emit(type, info) {
    if (callbacks[type]) callbacks[type].call(info)
  }
}
