Here's an updated version of your README, reflecting the switch to using the `IslamicCalendar` instead of the Umm Al-Qura calendar and emphasizing its broader support:

---

# HijriDatePickerPlus

**HijriDatePickerPlus** is an advanced Android date picker built using Jetpack Compose, designed for selecting dates in the Hijri (Islamic) calendar. The picker now uses the widely supported `IslamicCalendar` class, which offers flexibility and broader compatibility across regions, although it is not specific to Saudi Arabia like Umm Al-Qura.

## Features

- Full support for the **Islamic Calendar** system
- Custom date picker interface using Jetpack Compose
- Year selection with smooth scrolling and customizable font sizes
- Day picker automatically adjusts for the correct number of days in each Hijri month
- Ensures the app doesn't crash when selecting an invalid date in a Hijri month
- Lightweight and responsive UI for a smooth user experience

## Why Use IslamicCalendar?

Unlike the Umm Al-Qura calendar, which is specific to Saudi Arabia and has known issues in some cases, **IslamicCalendar** provides broader support for the global Muslim community. It offers better flexibility and accuracy for most use cases and regions, making it a more reliable option for international users who may not need Umm Al-Qura specificity.

## How to Use

1. Clone the repository: `git clone https://github.com/YourUsername/HijriDatePickerPlus.git`
2. Open the project in Android Studio
3. Build and run the project on an Android device or emulator
4. Incorporate the Hijri date picker into your project by copying the necessary components from the codebase

## Fixes and Solutions

HijriDatePickerPlus implements a critical fix for date validation in the Hijri calendar, ensuring that selecting a day beyond the valid number of days in a month (e.g., selecting the 30th day in a month with only 29 days) doesn't cause the app to crash. The picker automatically adjusts the selected day to the correct number of days in each month.

## Installation

To install and use **HijriDatePickerPlus** in your project:

1. Download or clone the project from GitHub.
2. Open the project in Android Studio.
3. Run the app or extract relevant components to use in your own application.

## Customization

You can customize the appearance and behavior of the HijriDatePickerPlus by adjusting the following:

- Modify the year and month picker layouts to fit your UI needs.
- Change the font sizes, colors, or padding for year and day selections.
- Use the `IslamicCalendar` class directly for further custom functionality or region-specific adaptations.

## Contributing

If you'd like to contribute to **HijriDatePickerPlus**, feel free to submit a pull request or open an issue for any bugs or features you'd like to suggest.

### How to Contribute

1. Fork the repository and create a new branch for your feature or bug fix.
2. Make your changes and ensure they follow the project's code style.
3. Submit a pull request with a clear explanation of the changes.

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## Contact

For any questions or support regarding this project, you can contact the project maintainer at: [mohamed.ma872@gmail.com](mailto:mohamed.ma872@gmail.com)

---

This README explains that the switch to the `IslamicCalendar` provides a more globally applicable solution, while still highlighting the key features and improvements of the Hijri Date Picker.
