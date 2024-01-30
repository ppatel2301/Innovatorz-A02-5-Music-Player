# Architecture

This application has three layers for Presentation, Logic and Data Persistence. These layers are used to enforce
single responsibility. Each layer has its own package and directory in the repo and are named accordingly.

- presentation
- logic
- persistence

The application is a music management system with its own music library. This music library belongs in the Data
Persistence layer, while the file decoding is in the **NEEDS REVISION** Logic layer and the music player UI is
in the presentation layer. There are classes that belong in more than one layer, like MusicTrack. These classes are
contained in the `objects` directory and package.

## Architecture Diagram

``` mermaid
---
title: Domain-Specific Objects
---

class Diagram
    DSO <|-- Artist
    DSO <|-- Song
    DSO: + uuid4 id
    DSO: + String name

        

```
