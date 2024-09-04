# HijriDatePickerPlus

**HijriDatePickerPlus** is an advanced Android date picker built using Jetpack Compose, designed for selecting dates in the Hijri (Islamic) calendar, with full support for Umm Al-Qura Calendar calculations. This project includes features like year, month, and day selections, and ensures the app doesn't crash when selecting an invalid date in a Hijri month.

## Features

- Full support for the Hijri (Umm Al-Qura) calendar system
- Custom date picker interface using Jetpack Compose
- Year selection with smooth scrolling and customizable font sizes
- Day picker automatically adjusts for the correct number of days in each Hijri month
- Crash prevention when selecting a day greater than the valid days of the month
- Lightweight and responsive UI for a smooth user experience

## How to Use

1. Clone the repository: `git clone https://github.com/YourUsername/HijriDatePickerPlus.git`
2. Open the project in Android Studio
3. Build and run the project on an Android device or emulator
4. Incorporate the Hijri date picker into your project by copying the necessary components from the codebase

## Fixes and Solutions

HijriDatePickerPlus implements a critical fix for an issue in the Umm Al-Qura calendar, where selecting a day beyond the maximum number of days in a Hijri month (e.g., selecting the 30th day in a month with only 29 days) caused crashes. This project automatically adjusts the selected day to prevent errors, ensuring a stable user experience.

## Installation

To install and use **HijriDatePickerPlus** in your project:

1. Download or clone the project from GitHub
2. Open the project in Android Studio
3. Run the app or extract relevant components to use in your own application

## Customization

You can customize the appearance and behavior of the HijriDatePickerPlus by adjusting the following:

- Modify the year and month picker layouts to fit your UI needs
- Change the font sizes, colors, or padding for year and day selections
- Use the `IslamicCalendar` directly for further custom functionality

## Contributing

If you'd like to contribute to **HijriDatePickerPlus**, feel free to submit a pull request or open an issue for any bugs or features you'd like to suggest.

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## Contact

For any questions or support regarding this project, you can contact the project maintainer at: [mohamed.ma872@gmail.com](mailto:mohamed.ma872@gmail.com)
