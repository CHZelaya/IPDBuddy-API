package oosd.sait.ipdbuddyapi.enums;

public enum Billables {
    INSULATION,
    DRYWALL,

    //* Max of $75
    FIRE_CAULKING,

    //* Scaffolding is paid at $25 per section
    SCAFFOLDING,

    //* Garage bulkhead $35 per side
    HIGH_GARAGE_BULKHEAD,

    //* Pinch point strips $40 per Single-Family house
    //* $50 per duplex
    PINCH_POINT_STRIPS,
    SINGLE_FAMILY_HOUSE,
    DUPLEX,

    //* Poly only $25 - $50
    POLY_ONLY,

    //* Fire caulking ladder runs zero lot lines Mattamy Homes $25 per floor
    FIRE_CAULKING_MATTAMY_HOUSE,

    SCRAP_OUT,
    SUITED_MECH_ROOM_RES_BAR,

    //* Suited Mech Room Ceilings
    //* $500 for Steel, $300 for board only, if two mechs, second is $150
    STEEL_FRAMING_AND_BOARD,
    BOARD_ONLY,

    //* If two mech room ceiling, second is $100.
    FIRE_TAPING_MECH_ROOM_CEILING,

} //! Class
