# Workflow

The workflow consists of three steps

1. collectBlocks -> Wait to collect all (N) blocks
2. createASnap -> Look at blocks (N) voting for blocks (N-1) and create a snap (N-1)
3. voteAndCreateABlock -> Create block (N+1) with your voting for (N) using Snap (N-1)

