# tollfee-calculator
Main Requirement:
To use the multiple cities the code should be used City factory (factory design pattern or abstract factory need more extendibility) for toll fee (each city can have separate toll fee calculations)

Enhancements:
The method “getTollFee” method should refactor for each city-based rate calculation. Each city can have its own calculation for toll fees (can be extends later city and vehicle vice or city vise parking if needed).

The “isTollFreeDate” The "year" can be moved to property configuration. (If there in any need of multiple year configuration can use factory for year).

The “Integer” is a barrier to decimals output for toll fees, which should be refactored with double values.

The method “isTollFreeVehicle” can be make for dynamic selection without using multiple equals method. (also, it’s not good implementation each city to keep same tall free vehicles list it’s enchantable).

Sequential Process: Since this is a pure java code, it supports only a sequential process which is not a good implementation for a public solution. We must use a threaded environment.

Solution could make as webservice solution (component) easily if they required such implementation with Maven, docker and Spring.

The code is not used any support of java framework like spring which will be latest and supportive many ways to build, enhance and delivery the code.

Note: Since the given code is production working code, I’m not trying to modify the logics which used for calculations.

Request:(POST)
http://localhost:8085/tollfee/calculate
JSON:
{
"cityName":"SOLNA",
"vehicle":"CAR",
"tollFeeYear":"2013",
"dates":["2013-01-23T06:00","2013-01-23T12:34"]
}