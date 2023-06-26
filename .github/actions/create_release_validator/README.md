# create_release

Github Action to create a new release for the context repository.

## Inputs 

### `gpg_private_key`
**Required** GPG private key for commit and tag signing
### `gpg_passphrase`
**Required** GPG Passphrase to import private-key
### `token`
**Required** [Personal Accesss Token (PAT)](https://docs.github.com/en/authentication/keeping-your-account-and-data-secure/creating-a-personal-access-token) to trigger the workflow.
### `name`
Name of the release. defaults to tag name
### `release_type`
**Required** Indicator of the release type. Must be any of the following strings: 
- major
- minor
- patch
**Required** Indicator of the release module. Must be any of the following strings:
- app-ext
- app-int


## Outputs
None

## Usage

<pre>
name: "🎁 Create Release"

on:
  workflow_dispatch:
    inputs:
      release_type:
        description: "Specify semver type of release"
        required: true
        type: choice
        options:
          - major
          - minor
          - patch
      release_name:
        description: 'Specify name of release (defaults to tag_name)'
        type: string
      release_module_name:
        description: 'Specify which module to release'
        required: true
        type: choice
        options:
          - app-ext
          - app-int

concurrency:
  group: release

jobs:

  release:
    runs-on: ubuntu-latest

    steps:     

      - uses: actions/checkout@v3
        with:
          fetch-depth: 0
          persist-credentials: false

      - name: install_shared_actions
        uses: ./.github/actions/install_shared_actions
        with:
          token: ${{secrets.REPO_ACCESS_TOKEN}}

      - name: create_release
        uses: ./.github/actions/shared/create_release
        with:
          gpg_private_key: ${{secrets.BOT_GPG_PRIVATE_KEY}}
          gpg_passphrase: ${{secrets.BOT_GPG_PASSPHRASE}}
          token: ${{secrets.REPO_ACCESS_TOKEN}}
          name: ${{inputs.release_name}}
          release_type: ${{inputs.release_type}}
</pre>