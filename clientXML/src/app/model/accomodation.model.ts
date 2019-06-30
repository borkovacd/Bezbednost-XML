import {CityModel} from './city.model';
import {AgentModel} from './agent.model';
import {TypeAccomodationModel} from './typeAccomodation.model';

export class AccomodationModel {

  public id: number;
  public name: string;
  public address: string;
  public city: CityModel;
  public agent: AgentModel;
  public typeAccomodation: TypeAccomodationModel;
  public pic: string;

}
