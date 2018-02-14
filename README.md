# TaskBuilder
A framework for building complex programs based upon more simple well-defined ones

## Features
* Supports virtually any language running on Unix platform.
* Allows the ability to mix different parts of code written in different languages to perform a task.
* Runs on any platform that supports bash shell.

## Language
* Java
* bash

## Usage
* Install bash, Java Runtime Environment (JRE), any compilers, libraries necessary for compiling and running code in command line interface.
* Download binary file https://github.com/voquangphu/TaskBuilder/blob/master/directory/TaskBuilder.jar
* Setup development enviroment (see below).
* Start using by providing simple programs, task building rules, task parameters... (more below).

## Environment setup
* Create a working directory (i.e. ~/TaskBuilder)
* Copy TaskBuilder.jar into working directory.
* Copy the default config file https://github.com/voquangphu/TaskBuilder/blob/master/out/production/TaskBuilder/config into working directory.
* Assuming using the default config file, create the following files in working directory.
  * action-map: mapping of actions and simple actions from which they are created. An action can have multiple inputs and one output. The file format can be referenced here: https://github.com/voquangphu/TaskBuilder/blob/master/out/production/TaskBuilder/action-map
  * command-list: list of commands to perform. The commands are then parsed accordingly to action-map definition and executed. The file format can be referenced here: https://github.com/voquangphu/TaskBuilder/blob/master/out/production/TaskBuilder/command-list

## Configuration
