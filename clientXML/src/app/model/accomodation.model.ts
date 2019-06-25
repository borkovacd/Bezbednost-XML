import {CityModel} from './city.model';
import {AgentModel} from './agent.model';

export class AccomodationModel {

  public id: number;
  public name: string;
  public address: string;
  public city: CityModel;
  public agent: AgentModel;

}
