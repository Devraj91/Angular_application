package com.nasscom.einvoice.entity;
import java.util.List;
public class CRMLiveMemberWrapper
{
    private data data;
	public data getData() {
		return data;
	}

	public void setData(data data) {
		this.data = data;
	}

	public class data
	{
	private List<String> memberIdList;
	public data()
	{
		
	}
	public List<String> getMemberIdList() {
		return memberIdList;
	}

	public void setMemberIdList(List<String> memberIdList) {
		this.memberIdList = memberIdList;
	} 
	}
}
