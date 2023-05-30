# Amature Hour Event Organizer

## Description
There are many niche and small communities which would benefit from a single platform where users can plan events to share with others; fostering a sense of community and sharing a love for their hobbies.  We aim to build that platform.

## SQL (4 hours)

### User table
    - Name
    - Bio
    - Email
    - User Id
    - Role Id

### Roles

    - Role Id
    - Role Title

### Event

    - Event Id
    - Host Id
    - Event Description
    - Address
    - City
    - State
    - Date
    - Title
    - Max Capacity
    - Rating

### Rating

    - Id
    - User Id
    - Event Id
    - Comment
    - Score

### Rsvp

    - Rsvp Id
    - Event Id
    - Member Id

### Tag

    - Tag Id
    - Tag name
    - Event Id

### Login

    - Login id
    - Username
    - Password

### State

    - Id
    - Name

### City

    - Id
    - State Id
    - Name

## Backend

### Domain

### Service

### Models