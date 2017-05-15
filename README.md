# paymet-test
PayMet technical test for the Android Developer position This project uses the following technologies & patterns:

- **MVP** design pattern (with retained presenters)
- **Mockito** for mocking objects in **unit tests**
- **ButterKnife** for view injection
- **Dagger2** for dependency injection
- **Retrofit2 + RxJava2** for consuming Marvel API
- **EventBus** for communication between activities & fragments
- **Fresco** for image loading & caching

The app takes advantage of available width to display the list of comics. In regular screens, they're shown in a 2-column grid.
If more width is available, they'll be shown in a 3-column grid instead (when running on tablets or in landscape mode on regular phones)
