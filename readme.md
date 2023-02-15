# Confidence Coin
[Confidence Coin](https://confidence-coin.com/) is a cryptocurrency project that implements the [Flash consensus algorithm](https://confidence-coin.com/Flash-Consensus-algorithm/) and the [Desentralized Trusted Party (DTP)](https://confidence-coin.com/dtp/). The code is still in development, but it's getting into a shareable state(Baby steps).

# Core logic blocks
The project has implemented the three core logic blocks:

 - `collectBlocks` 
 - `createASnap` 
 - `voteAndCreateABlock` 

# Implementation
The project has implemented the `Snap`, `Dtp`, and `UpdateTransaction` classes. The `UpdateTransaction` can be added to the `Snap`, and the verification process is carried out when creating a new block.

The project has also implemented some unit tests, but using object classes was a mistake, and there is a need to switch to DI.

# Contributing
If you would like to contribute to the project, please open an issue first with your plan so that the work can be coordinated.

Learn more about Confidence Coin at the official website: confidence-coin.com, and read about [DTP](https://confidence-coin.com/dtp/) and the [Flash consensus algorithm](https://confidence-coin.com/Flash-Consensus-algorithm/).
