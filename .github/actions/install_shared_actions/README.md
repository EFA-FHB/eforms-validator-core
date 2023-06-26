## install_shared_actions

Performs a git checkout of project repository [efa_cicd](https://github.com/EFA-FHB/efa_cicd) and 
copies all `shared_actions` under `.github` to make them accessible to repository workflows. 

Specify a `tag` or `branch` as input to perform the checkout of an existing revision.

A `tag` is treated with higher priority than a `branch`. If both are specified, `tag` wins.

If neither `tag` or `branch` are specified, `branch` will fallback to the `main` branch.

`tag` or `branch` must exist, otherwise the checkout will fail.

**Important:**
All shared_actions will be downloaded to the directory `.github/actions/shared`. 

##Inputs

Name | Mandatory | Description | Default | Example
-- | -- | -- | -- | --
`token` | `yes` | [Personal Accesss Token (PAT)](https://docs.github.com/en/authentication/keeping-your-account-and-data-secure/creating-a-personal-access-token) to trigger the workflow. |  | 
`tag` | `no` | Reference to an existing tag |  | `v1.4.0` 
`branch` | `no` | Reference to an existing branch |  | `development` 

##Outputs
**none**

##Usage

#### Checking out branch `refs/heads/main`
<pre>
      - name: install_shared_actions
        uses: ./.github/actions/install_shared_actions
        with:
          token: ${{secrets.REPO_ACCESS_TOKEN}}
</pre>

#### Checking out branch `refs/heads/feature/foo-bar`
<pre>
      - name: install_shared_actions
        uses: ./.github/actions/install_shared_actions
        with:
          token: ${{secrets.REPO_ACCESS_TOKEN}}
          branch: "feature/foo-bar"
</pre>

#### Checking out branch `refs/tags/v1.0.0`
<pre>
      - name: install_shared_actions
        uses: ./.github/actions/install_shared_actions
        with:
          token: ${{secrets.REPO_ACCESS_TOKEN}}
          branch: "feature/foo-bar"
          tag: "v1.0.0"
</pre>



