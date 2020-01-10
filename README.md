# Titan plugin launcher

This project is designed to launch and manage plugins built using the
[Titan Remote SDK for Go](https://github.com/titan-data/remote-sdk-go). The SDK is based on the 
[HashiCorp go-plugin](https://github.com/hashicorp/go-plugin) framework, which allows arbitrary plugins by
invoking them as subprocesses and communicating over gRPC. This will help in the transition of Titan from Kotlin
from GoLang, even if `titan-server` remains written in Kotlin for a lengthy period of time.

## Contributing

This project follows the Titan community best practices:

  * [Contributing](https://github.com/titan-data/.github/blob/master/CONTRIBUTING.md)
  * [Code of Conduct](https://github.com/titan-data/.github/blob/master/CODE_OF_CONDUCT.md)
  * [Community Support](https://github.com/titan-data/.github/blob/master/SUPPORT.md)

It is maintained by the [Titan community maintainers](https://github.com/titan-data/.github/blob/master/MAINTAINERS.md)

For more information on how it works, and how to build and release new versions,
see the [Development Guidelines](DEVELOPING.md).

## License

This is code is licensed under the Apache License 2.0. Full license is
available [here](./LICENSE).
