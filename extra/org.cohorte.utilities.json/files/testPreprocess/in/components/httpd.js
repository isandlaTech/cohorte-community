// description of the hello world component that correspond to a webapp hello world
{
	"id":"helloWorld",
	"type":"docker",
	
	"docker":{
		"image":"dimensions/httpd",
		"version":"1.0.0",
		"tyoe":"d",
		"states":[
			{
				"creating":{
					"steps":{"$include":"steps/creating_httpd.js"}
				},
				"starting":{
					"steps":{"$include":"steps/starting_httpd.js"}
				},
				"validating":{
					"steps":{"$include":"steps/validating_httpd.js"}
				},
				"updating":{
					"steps":{"$include":"steps/updating_httpd.js"}
				}
			}
		"volume":[
			{
				"container":"/opt/conf",
				"host":"/root/grandest/httpd/conf/"
			}
		],
	}
}