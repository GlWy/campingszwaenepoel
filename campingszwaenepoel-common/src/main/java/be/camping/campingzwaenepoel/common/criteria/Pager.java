package be.camping.campingzwaenepoel.common.criteria;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;

/**
 * Class containing paging properties
 * 
 * @author NeckebB
 * @author craig.moore@cropdesign.com
 * @version 1.0 Initial version
 * @version 2.0 Added toString, hashcode, and equals methods.
 */
public class Pager implements Serializable {

	private static final long serialVersionUID = -5378217454039279390L;

	/**
	 * Page number to fetch
	 */
	private int pageNumber;

	/**
	 * Size of a single page
	 */
	private int pageSize;


	/**
	 * Create a new {@link Pager} with the given pageNumber and pageSize.
	 * 
	 * @param pageNumber
	 *            Current page numner
	 * @param pageSize
	 *            Size of the page
	 */
	public Pager(int pageNumber, int pageSize) {
		this.pageNumber = pageNumber;
		this.pageSize = pageSize;
	}


	/**
	 * Create an instance of {@link Pager}
	 */
	public Pager() {
		// Nothing to do here...
	}


	/**
	 * @return the pageNumber
	 */
	public int getPageNumber() {
		return pageNumber;
	}


	/**
	 * @param pageNumber
	 *            the pageNumber to set
	 */
	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}


	/**
	 * @return the pageSize
	 */
	public int getPageSize() {
		return pageSize;
	}


	/**
	 * @param pageSize
	 *            the pageSize to set
	 */
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}


	protected EqualsBuilder getEqualsBuilder(Object obj) {
		Pager pager = (Pager) obj;
		return new EqualsBuilder().append(getPageNumber(), pager.getPageNumber()).append(getPageSize(),
				pager.getPageSize());
	}


	protected HashCodeBuilder getHashCodeBuilder() {
		return new HashCodeBuilder(73, 19).append(getPageNumber()).append(getPageSize());
	}


	@Override
	public int hashCode() {
		return getHashCodeBuilder().hashCode();
	}


	/**
	 * Checks
	 * <ul>
	 * <li>If the {@link Object} is null</li>
	 * <li>If the {@link Object} refers to the same location in memory</li>
	 * <li>If the {@link Object} is the same class as the {@link Pager}</li>
	 * </ul>
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (obj == this) {
			return true;
		}
		if (obj.getClass() != getClass()) {
			return false;
		}
		return getEqualsBuilder(obj).isEquals();
	}


	protected ToStringBuilder getToStringBuilder() {
		return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).append("Page Number", getPageNumber())
				.append("Page Size", getPageSize());
	}


	@Override
	public String toString() {
		return getToStringBuilder().toString();
	}

}
