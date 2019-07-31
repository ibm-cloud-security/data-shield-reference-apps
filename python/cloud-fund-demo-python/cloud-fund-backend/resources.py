
import requests
import json

class Resource_group:
        #def __init__(self, token, id, name, region='us-south', url='https://resource-controller.ng.bluemix.net/v1/resource_instances'):
        #    self.id = id
        #    self.name = name
        #    self.token = token
        #    self.url = url
        #    self.region = region

        def __init__(self, config, id, name):
            if config['token'] is None:
                config['token'] = Token(config) 
            self.token = config['token']
            self.url = config['url']['resource_controller_url']
            self.region = config['account']['region']
            self.id = id
            self.name = name

        def __getResources(self):
            bearer_token = 'Bearer '+ self.token.getAccessToken()
            headers = {'content-type': 'application/x-www-form-urlencoded', 'Accept': 'application/vnd.ibm.collection+json', 'authorization': bearer_token}
            PARAMS = {'region_id': self.region, 'resource_group_id': self.id}
            r = requests.get(url = self.url, params=PARAMS, headers=headers)
            #print json.dumps(r.json(), indent=4, sort_keys=True)
            resources = r.json()['resources']
            return resources

        def getResources(self,type=None, name=None):
            resource_list = [];
            resources = self.__getResources();
            for resource in resources:
                _resource_group_id = resource['resource_group_id']
                _name = resource['name']
                _crn = resource['crn']
                _type = _crn.split(':')[4]
                if type is None:
                    resource_list.append(Resource(id=resource['guid'], name=_name, crn=_crn, resource_group_id=_resource_group_id, type=_type))
                elif type ==_type:
                    if name is None:
                         resource_list.append(Resource(id=resource['guid'], name=_name, crn=_crn, resource_group_id=_resource_group_id, type=_type))
                    elif name == _name:
                         resource_list.append(Resource(id=resource['guid'], name=_name, crn=_crn, resource_group_id=_resource_group_id, type=_type))
                    
            return resource_list

class Account:
        def __init__(self, token, url="https://resource-manager.bluemix.net/v1/resource_groups"):
            self.token = token
            self.URL = url

        def __init__(self, config):
            if config['token'] is None:
                config['token'] = Token(config) 
            self.config = config
            self.token = config['token']
            self.URL = config['url']['resource_manager_url']

        def __getResourceGroups(self):
            bearer_token = 'Bearer '+ self.token.getAccessToken()
            headers = {'content-type': 'application/x-www-form-urlencoded', 'Accept': 'application/vnd.ibm.collection+json', 'authorization': bearer_token}
            PARAMS = {}
            r = requests.get(url = self.URL, params=PARAMS, headers=headers)
            # check for error
            resource_groups = r.json()['resources']
            return resource_groups

        def getResourceGroups(self):
            resource_group_list = [];
            resource_groups = self.__getResourceGroups();
            for resource_group in resource_groups:
                resource_group_list.append(Resource_group(self.config, resource_group['id'].encode('utf8'), resource_group['name'].encode('utf8')))
                
            return resource_group_list

        def getResourceGroup(self, resource_group_name='default'):
            resource_groups = self.__getResourceGroups();
            for resource_group in resource_groups:
                if resource_group_name == resource_group['name'].encode('utf8'):
                    return(Resource_group(self.config, resource_group['id'].encode('utf8'), resource_group['name'].encode('utf8')))
            return None


class Resource:
        def __init__(self, name, id, resource_group_id, type, crn):
            self.name = name
            self.id = id
            self.resource_group_id = resource_group_id
            self.resource_type = type
            self.crn = crn
