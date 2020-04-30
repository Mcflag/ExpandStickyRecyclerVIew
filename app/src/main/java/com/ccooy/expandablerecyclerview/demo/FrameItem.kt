package com.ccooy.expandablerecyclerview.demo

class FrameItem(var content: Frame) {

  override fun toString(): String {
    return "content: " + content.javaClass.simpleName
  }
}
