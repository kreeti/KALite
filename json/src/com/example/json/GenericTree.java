package com.example.json;

import org.json.JSONException;
import org.json.JSONObject;

public class GenericTree {
    public String data;
    public GenericTree root = null;
    public GenericTree right = null;
    public GenericTree left = null;
    GenericTree temp1 = null;
	public GenericTree(JSONObject jsonObject) {
		// TODO Auto-generated constructor stub
		try {
			this.data = jsonObject.getString("title");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		root = this;
	}

	public void addSibling(JSONObject jsonObject,GenericTree node) {
		// TODO Auto-generated method stub
		GenericTree temp = node;
	    while(temp.right != null)
	    	temp = temp.right;
	    GenericTree sibling = new GenericTree(jsonObject);
	    temp.right = sibling;
		
	}
    public boolean parentMatch(String parent,GenericTree node)
    {
    	boolean flag = false;
		temp1 = node.left;
		if( temp1.data != parent)
		{
			while(temp1.data != parent)
				temp1 = temp1.right;		
		}
		else
			flag = true;
		if(temp1.right == null && flag == false)
		{	temp1 = node.left.left;
		    flag = parentMatch(parent, node);
		}
    	return flag;
    }
	public void addChild(String parent, JSONObject jsonObject) {
		// TODO Auto-generated method stub
		boolean flag;
		flag = checkForSibling(parent,root,jsonObject);
		if(!flag)
		{
			flag = parentMatch(parent,root);
		    if (flag)
		    {
		       GenericTree child = new GenericTree(jsonObject);
		       temp1.left = child;
		    }
		}
	}

	private boolean checkForSibling(String parent, GenericTree root2, JSONObject jsonObject) {
		// TODO Auto-generated method stub
		parentMatch(parent, root2);
		if (temp1.left != null)								// if parent already has a child,then this object will be added as a sibling
		{
			addSibling(jsonObject,temp1);
			return true;
		}
		return false;
	}

	public boolean ElementPresent(GenericTree node) {
		// TODO Auto-generated method stub
		GenericTree temp = root;
		while(temp.left != null)
			{
			    temp = temp.left;
			    while (temp.right !=null)
			    	{   
			    	    if(temp.data == node.data)
			    		   return true;
			    	    temp = temp.right;
			    	}
			}
				
		return false;
	}
	
}
