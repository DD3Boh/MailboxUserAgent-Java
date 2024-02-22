# MailBox User Agent

This repository contains a MailBox User Agent written in Java for a Programming II university course.  
The program is designed for reading and writing a simplified version of the official email standard.

Please note that this program is not intended for sending messages, but rather just saving them on the disk in the encoded format.

## Features

The MailBox User Agent supports the following features:

- One plain text part, which can be encoded in UTF-8 (encoded as Base64) or US-ASCII (plain)
- One HTML part, always encoded as Base64
- Multiple attachment files, always encoded as Base64

## Usage

To use the program, follow these steps:

1. Clone the repository.
2. Build the Java program.
3. Run the `App.java` file located in `src/main/java/mua/`, passing a directory as a command line argument. This directory will be used as the root directory for the mailboxes and messages.

The supported commands for the command line program are:

- `LSM`: List the available mailboxes.
- `LSE`: List the messages when inside a mailbox.
- `MBOX <N>`: Enter the mailbox with the specified number `N`.
- `READ <N>`: Read the message with the specified number `N`.
- `DELETE <N>`: Delete the message with the specified number `N`.
- `COMPOSE`: Compose a message in the current mailbox.
- `EXIT`: Stop the program.

Please note that this program is a proof of concept and is not meant for actual usage in any real environment, even though it does fulfill its intended purpose.

## License

This project is licensed under the GPL 3.0 or newer license.
