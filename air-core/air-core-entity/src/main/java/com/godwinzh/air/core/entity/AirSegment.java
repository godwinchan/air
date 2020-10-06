package com.godwinzh.air.core.entity;

import lombok.Data;

import java.math.BigInteger;
import java.util.List;

/**
 * @ClassName AirSegment
 * @Description TODO
 * @Author godwin
 * @Date 2020/9/15 22:41
 * @Version 1.0
 */
@Data
public class AirSegment {
//    @XmlElement(name = "SponsoredFltInfo")
//    protected SponsoredFltInfo sponsoredFltInfo;
    /**
     * 航班共享
     */
    protected CodeshareInfo codeshareInfo;
    //    @XmlElement(name = "AirAvailInfo")
    protected List<AirAvailInfo> airAvailInfo;
//    @XmlElement(name = "FlightDetails")
//    protected List<FlightDetails> flightDetails;
//    @XmlElement(name = "FlightDetailsRef")
//    protected List<FlightDetailsRef> flightDetailsRef;
//    @XmlElement(name = "AlternateLocationDistanceRef")
//    protected List<AlternateLocationDistanceRef> alternateLocationDistanceRef;
//    @XmlElement(name = "Connection")
//    protected Connection connection;
//    @XmlElement(name = "SellMessage", namespace = "http://www.travelport.com/schema/common_v41_0")
//    protected List<String> sellMessage;
//    @XmlElement(name = "RailCoachDetails")
//    protected List<RailCoachDetails> railCoachDetails;
//    @XmlAttribute(name = "OpenSegment")
//    protected Boolean openSegment;
//    @XmlAttribute(name = "Group", required = true)
//    protected int group;
    /**
     * 航司
     */
    protected String carrier;
//    @XmlAttribute(name = "CabinClass")
//    protected String cabinClass;
    /**
     * 航班号
     */
    protected String flightNumber;
//    @XmlAttribute(name = "ClassOfService")
//    protected String classOfService;
//    @XmlAttribute(name = "ETicketability")
//    protected TypeEticketability eTicketability;
    /**
     * 机型
     */
    protected String equipment;
//    @XmlAttribute(name = "MarriageGroup")
//    protected Integer marriageGroup;
//    @XmlAttribute(name = "NumberOfStops")
//    protected Integer numberOfStops;
//    @XmlAttribute(name = "Seamless")
//    protected Boolean seamless;
//    @XmlAttribute(name = "ChangeOfPlane")
//    protected Boolean changeOfPlane;
//    @XmlAttribute(name = "GuaranteedPaymentCarrier")
//    protected String guaranteedPaymentCarrier;
//    @XmlAttribute(name = "HostTokenRef")
//    protected String hostTokenRef;
//    @XmlAttribute(name = "ProviderReservationInfoRef")
//    protected String providerReservationInfoRef;
//    @XmlAttribute(name = "PassiveProviderReservationInfoRef")
//    protected String passiveProviderReservationInfoRef;
//    @XmlAttribute(name = "OptionalServicesIndicator")
//    protected Boolean optionalServicesIndicator;
//    @XmlAttribute(name = "AvailabilitySource")
//    protected String availabilitySource;
//    @XmlAttribute(name = "APISRequirementsRef")
//    protected String apisRequirementsRef;
//    @XmlAttribute(name = "BlackListed")
//    protected Boolean blackListed;
//    @XmlAttribute(name = "OperationalStatus")
//    protected String operationalStatus;
//    @XmlAttribute(name = "NumberInParty")
//    protected Integer numberInParty;
//    @XmlAttribute(name = "RailCoachNumber")
//    protected String railCoachNumber;
//    @XmlAttribute(name = "BookingDate")
//    @XmlSchemaType(name = "date")
//    protected XMLGregorianCalendar bookingDate;
//    @XmlAttribute(name = "FlownSegment")
//    protected Boolean flownSegment;
//    @XmlAttribute(name = "ScheduleChange")
//    protected Boolean scheduleChange;
//    @XmlAttribute(name = "BrandIndicator")
//    protected String brandIndicator;
//    @XmlAttribute(name = "ParticipantLevel")
//    protected String participantLevel;
//    @XmlAttribute(name = "LinkAvailability")
//    protected Boolean linkAvailability;
//    @XmlAttribute(name = "PolledAvailabilityOption")
//    protected String polledAvailabilityOption;
//    @XmlAttribute(name = "AvailabilityDisplayType")
//    protected String availabilityDisplayType;
    /**
     * 出发
     */
    protected String origin;
    /**
     * 到达
     */
    protected String destination;
    /**
     * 起飞时间
     */
    protected String departureTime;
    /**
     * 起飞航站楼
     */
    protected String originTerminal;
    /**
     * 到达航站楼
     */
    protected String destinationTerminal;
    /**
     * 到达
     */
    protected String arrivalTime;
    /**
     * 飞行时间
     */
    protected BigInteger flightTime;
    /**
     * 旅行时间
     */
    protected BigInteger travelTime;
    /**
     * 里程
     */
    protected BigInteger distance;
    /***/
    protected String providerCode;
    /**
     * 供应代码
     */
    protected String supplierCode;
}
