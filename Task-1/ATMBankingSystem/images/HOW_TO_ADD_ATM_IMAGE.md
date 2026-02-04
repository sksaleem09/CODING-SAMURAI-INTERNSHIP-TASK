# How to Add Your ATM Background Image

## Quick Steps:

1. **Save your ATM image** to the `images/` folder with one of these names:
   - `welcome.jpg` - For the welcome screen
   - `create_account.jpg` - For create account screen
   - `pin.jpg` - For PIN generation screen
   - `login.jpg` - For login screen
   - `menu.jpg` - For main menu screen
   - `balance.jpg` - For balance enquiry screen
   - `deposit.jpg` - For deposit screen
   - `withdraw.jpg` - For withdraw screen

2. **Supported formats:** `.jpg`, `.jpeg`, `.png`

3. **Recommended size:** 800x600 pixels or larger (will be scaled automatically)

## To Use the Same Image for All Screens:

If you want to use the same ATM image for all screens, simply:
- Copy your image file and name it as each of the files above
- OR rename your image to match each screen name

## Example:

If your ATM image is named `atm_background.jpg`:

1. Copy it 8 times to the `images/` folder
2. Rename them to:
   - `welcome.jpg`
   - `create_account.jpg`
   - `pin.jpg`
   - `login.jpg`
   - `menu.jpg`
   - `balance.jpg`
   - `deposit.jpg`
   - `withdraw.jpg`

## Notes:

- The application will automatically scale the image to fit the window
- If an image is missing, the app will use a professional gradient background instead
- Images are loaded from local files only (no internet required)
