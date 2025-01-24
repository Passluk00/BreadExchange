import {ChangeDetectorRef, Component, Inject, Input, OnInit, PLATFORM_ID} from '@angular/core';
import {FrontEndControllerService} from "../../../../services/services/front-end-controller.service";
import {Address} from "../../../../services/models/address";
import {GoogleMapsModule} from "@angular/google-maps";
import {HttpClient} from "@angular/common/http";
import {isPlatformBrowser, NgIf} from "@angular/common";

@Component({
  selector: 'app-address',
  imports: [
    GoogleMapsModule,
    NgIf
  ],
  templateUrl: './address.component.html',
  standalone: true,
  styleUrl: './address.component.scss'
})
export class AddressComponent implements OnInit{

  @Input() data!: Address | undefined;

  constructor(
    private http: HttpClient,
    private cdr: ChangeDetectorRef,
    @Inject(PLATFORM_ID) private platformId: Object
  ) {
  }

  center: { lng: number; lat: number } = {lat: 0, lng:0};
  zoom: number =  15;
  api: string = "AIzaSyAT1Um6CCZMeeUhZCg9HpOoaKQqvlU4sxo"



ngOnInit() {
  if( isPlatformBrowser(this.platformId))
    this.getCordFromAddress()
}



  getCordFromAddress(){
    if(this.data) {
      let address: string = `${this.data.street} ${this.data.number} ${this.data.city} ${this.data.country}`;
      const url = `https://maps.googleapis.com/maps/api/geocode/json?address=${encodeURIComponent(address)}&key=${this.api}`;

      this.http.get(url).subscribe((response: any) => {
        if (response.status === 'OK' && response.result.length > 0) {
          const loc = response.result[0].geometry.location;
          this.center = {
            lat: loc.lat,
            lng: loc.lng
          };
          this.cdr.detectChanges()
          window.location.reload()
          console.info("Cordinate ottenute:" + loc.lat, loc.lng)

        } else {
          console.error("Errore nel recuperare le coordinate" + response.status)
        }
      })

    }
  }



}




