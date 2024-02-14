# Git Flow

As a group, we decided to use the Git Flow branching strategy. This strategy uses two "main" branches to
record the progress of the project and creates a branch for every feature.

The two "main" branches are called `main` and `dev`. The `main`
branch records the official releases while `dev` is the default
integration branch. This strategy results in having one single
merge to `main` per iteration, which is only done when all the
code in `dev` works correctly.

For this iteration we had issues committing the official release,
but we generally followed the one-branch-per-feature approach
while also having an integration branch separate from `main`.
