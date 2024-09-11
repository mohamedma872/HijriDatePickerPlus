![1725800829380](https://github.com/user-attachments/assets/bfc62b89-41bd-4cc1-bb18-44ac901b2a03)


# HijriDatePickerPlus

**HijriDatePickerPlus** is an advanced Android date picker built using Jetpack Compose, designed for selecting dates in the Hijri (Islamic) calendar. The picker now uses the widely supported `IslamicCalendar` class, offering flexibility and compatibility across various regions, with support for the "umalqura", "civil", or "islamic" calendar types.

## Features

- Full support for **Islamic Calendar** systems, including **Umm Al-Qura**, **Islamic Civil**, and **General Islamic**.
- Custom date picker interface using Jetpack Compose.
- Year selection with smooth scrolling and customizable font sizes.
- Day picker automatically adjusts for the correct number of days in each Hijri month.
- Ensures the app doesn't crash when selecting an invalid date in a Hijri month.
- Lightweight and responsive UI for a smooth user experience.

## Why Use IslamicCalendar?

While the **Umm Al-Qura** calendar is specific to Saudi Arabia and suitable for regional use, the **IslamicCalendar** provides broader support globally. It offers flexibility for international users with support for various calendar types, including **Islamic Civil** and **General Islamic** calendars.

## How to Use

1. Clone the repository: `git clone https://github.com/YourUsername/HijriDatePickerPlus.git`
2. Open the project in Android Studio.
3. Build and run the project on an Android device or emulator.
4. Incorporate the Hijri date picker into your project by copying the necessary components from the codebase.

## How to Use the Library

To use **HijriDatePickerPlus** in your Android project, follow these steps:

### 1. Add the Dependency

First, add the dependency to your project's `build.gradle` file. Make sure your project is set up to use JitPack by adding the JitPack repository in your `settings.gradle` or `build.gradle` (for Gradle version catalog users, include this in your version catalog file):

```groovy
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven { url = uri("https://jitpack.io") }
    }
}
```

Then, add the following to your app-level `build.gradle` file under `dependencies`:

```groovy
dependencies {
    implementation 'com.github.mohamedma872:HijriDatePickerPlus:Tag'
}
```

Replace `Tag` with the latest version, which you can find [here](https://jitpack.io/#mohamedma872/HijriDatePickerPlus).

### 2. Initialize and Display the Hijri Date Picker

To use the date picker, simply call the **HijriDatePickerPlus** component in your Composable function. Here's an example of how to integrate it into your app:

```kotlin
@Composable
fun ShowHijriDatePicker() {
    // Example of how to trigger the date picker using different calendar types
 HijriDatePickerButton()

OR
    showHijriDatePicker(
        initialYear = 1446, // Pass the initial year, e.g., current Hijri year
        initialMonth = 1,   // Initial month
        initialDay = 10,    // Initial day
        onDateSelected = { year, month, day ->
            // Handle date selected (year, month, day)
            println("Selected Date: $day-${getHijriMonthName(month)}-$year")
        },
        onConfirm = { year, month, day ->
            // Handle the final confirmed date
            println("Confirmed Date: $day-${getHijriMonthName(month)}-$year")
        },
        onDismissRequest = {
            // Handle the dismiss action
            println("Date Picker Dismissed")
        },
        calendarType = "umalqura" // Use "umalqura", "civil", or "islamic"
    )
}
```

### 3. Customize the Picker

You can modify the picker to suit your design needs by adjusting properties such as:

- **Year/Month/Day Display**: Modify the visual appearance by changing the text size, colors, and layout in the `HeaderSection`, `YearSelectionScreen`, and `FooterSection` components.
- **Hijri Calendar**: The library uses the `IslamicCalendar` class for date calculations. You can directly interact with this class for customization or region-specific adjustments.

### Getting the Current Date Using the Desired Calendar Type

You can easily get the current date based on the **Umm Al-Qura**, **Islamic Civil**, or **General Islamic** calendar using the following function:

For example, to get the current date for the selected calendar type:

```kotlin
// Get the current Hijri date using the desired calendar type
val currentHijriCalendar = getIslamicCalendar("umalqura")
val currentHijriYear = currentHijriCalendar.get(Calendar.YEAR)
val currentHijriMonth = currentHijriCalendar.get(Calendar.MONTH)
val currentHijriDay = currentHijriCalendar.get(Calendar.DAY_OF_MONTH)

println("Current Hijri Date: $currentHijriDay-${getHijriMonthName(currentHijriMonth)}-$currentHijriYear")
```



### 4. Customize the Picker

You can modify the picker to suit your design needs by adjusting properties such as:

- **Year/Month/Day Display**: You can modify the visual appearance by changing the text size, colors, and layout in the `HeaderSection`, `YearSelectionScreen`, and `FooterSection` components.
- **Hijri Calendar**: The library uses the `IslamicCalendar` class for date calculations. You can directly interact with this class if you need further customization or region-specific adjustments.



## Fixes and Solutions

**HijriDatePickerPlus** implements a critical fix for date validation in the Hijri calendar, ensuring that selecting a day beyond the valid number of days in a month (e.g., selecting the 30th day in a month with only 29 days) doesn't cause the app to crash. The picker automatically adjusts the selected day to the correct number of days in each month.

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

[![](https://jitpack.io/v/mohamedma872/HijriDatePickerPlus.svg)](https://jitpack.io/#mohamedma872/HijriDatePickerPlus)

---

This updated README includes the mention of the `getIslamicCalendar` function and an example of how to use it to get the current Hijri date. Let me know if you need any further adjustments!
