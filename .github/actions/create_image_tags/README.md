## create_image_tags

Produces a space separated string of tags calculated from the current version information and 
the branch reference. The version information is retrived from `gradle.properties` or `package.json` (in that order).
If neither file is present or does not contain a version information, the action exists with `exit code: 1`
and an error message. 

The tags produced comply with the [project branching strategy](https://confluence.nortal.com/display/BVU/New+branching+strategy)

The tags can be used for `docker build` or `docker tag` operations. 

## Inputs

Name | Mandatory | Description | Default | Example
-- | -- | -- | -- | --
`debug` | `no` | Whether to enable script debugging | `false` | 

## Outputs

Name | Description | Example
-- | -- | -- 
`tags` | Space separated list of image tags | `2.0.0-beta-123 latest`, `2.0.0-213` [More examples](https://confluence.nortal.com/display/BVU/New+branching+strategy)

##Usage

<pre>
      - name: create image tags
        id: create_image_tags
        uses: ./.github/actions/shared/create_image_tags
        with:
          release_module_name: ${{ module.name }}
</pre>


