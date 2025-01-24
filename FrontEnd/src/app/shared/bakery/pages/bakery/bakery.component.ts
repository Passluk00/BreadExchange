import {Component, Inject, OnInit, PLATFORM_ID} from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {CaroselloInfinitoComponent} from "../../components/carosello-infinito/carosello-infinito.component";
import {FrontEndControllerService} from "../../../../services/services/front-end-controller.service";
import {BakeryfrontEndResponse} from "../../../../services/models/bakeryfront-end-response";
import {NavbarComponent} from "../../../components/navbar/navbar.component";
import {FooterComponent} from "../../../components/footer/footer.component";
import {AddressComponent} from "../../components/address/address.component";

@Component({
  selector: 'app-bakery',
  imports: [
    CaroselloInfinitoComponent,
    NavbarComponent,
    FooterComponent,
    AddressComponent
  ],
  templateUrl: './bakery.component.html',
  standalone: true,
  styleUrl: './bakery.component.scss'
})
export class BakeryComponent implements OnInit{

  userId: number | undefined;
  data: BakeryfrontEndResponse = {}

  constructor(
    private route: ActivatedRoute,
    private frontEndService: FrontEndControllerService,
  ) {
  }

  ngOnInit(): void {
    this.route.params.subscribe(params =>{
      this.userId = +params['id']
      this.getData()
    })


  }

  getData(){
    if(this.userId) {
      this.frontEndService.bakeryDetail({
        id: this.userId
      }).subscribe({
        next: (res) => {
          this.data = res;
          console.error("dati: "+this.data.address?.name)
        },
        error: (err) => {
          console.error("Errore nel Fetch Dati: " + err)
        }
      })
    }
  }



}
