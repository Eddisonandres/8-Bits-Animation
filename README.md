# 8 Bits Animation

## Overview

8 Bits Animation is a Java-based application that generates and displays animated images from CSV files. Each CSV file contains numerical values that correspond to specific colors, which are then rendered as an 8-bit style animation.

## Features

- Load and display pixelated images based on numerical data
- Supports multiple frames for animations
- Customizable color mapping for visual effects
- Graphical User Interface (GUI) using Swing
- Error handling for invalid file formats

## Technologies Used

- **Java** (Core functionality)
- **Swing** (GUI components)
- **File I/O** (Reading CSV files)

## Project Structure

```
8-Bits-Animation/
│── src/
│   ├── Bitmap.java       # Handles bitmap representation of images
│   ├── Image.java        # Stores image objects
│   ├── ShowImages.java   # Main application for displaying images
│── Files/                # Folder to store CSV image data
│── README.md             # Project documentation
│── icono.png             # Application icon
│── Background.png        # Background image for the GUI
```

## Installation & Setup

1. Clone the repository:
   ```sh
   git clone https://github.com/Eddisonandres/8-Bits-Animation.git
   cd 8-bits-animation
   ```
2. Ensure you have Java installed (JDK 8 or later).
3. Compile the project:
   ```sh
   javac src/*.java
   ```
4. Run the application:
   ```sh
   java src.ShowImages
   ```

## Usage

1. **Create a new animation:** Enter a project name and input a CSV file with pixel values.
2. **Load an existing project:** Select from available projects and visualize stored animations.
3. **Customize colors:** Modify the `colorMap` in `Bitmap.java` to change the default color scheme.

## File Format

Each CSV file represents an image frame using numbers:

```
0 0 1 1 1 0 0
0 1 2 2 2 1 0
1 2 3 3 3 2 1
1 2 3 4 3 2 1
0 1 2 2 2 1 0
0 0 1 1 1 0 0
```

- **0** = White
- **1** = Red
- **2** = Blue
- **3** = Orange
- **4** = Black
- **5** = Yellow
- **6** = Gray
- **7** = Green
- **8** = Pink
- **9** = Cyan

Multiple CSV files with the same size can be used for animation.

## Future Improvements

- Add support for custom frame rates
- Implement more advanced animation controls
- Support additional file formats for input

## License

This project is open-source under the [MIT License](LICENSE).

## Author

Eddison Jimenez: (https://github.com/Eddisonandres)


