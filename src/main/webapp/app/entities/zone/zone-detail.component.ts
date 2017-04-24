import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager , JhiLanguageService  } from 'ng-jhipster';

import { Zone } from './zone.model';
import { ZoneService } from './zone.service';

@Component({
    selector: 'jhi-zone-detail',
    templateUrl: './zone-detail.component.html'
})
export class ZoneDetailComponent implements OnInit, OnDestroy {

    zone: Zone;
    private subscription: any;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: EventManager,
        private jhiLanguageService: JhiLanguageService,
        private zoneService: ZoneService,
        private route: ActivatedRoute
    ) {
        this.jhiLanguageService.setLocations(['zone']);
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInZones();
    }

    load(id) {
        this.zoneService.find(id).subscribe((zone) => {
            this.zone = zone;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInZones() {
        this.eventSubscriber = this.eventManager.subscribe('zoneListModification', (response) => this.load(this.zone.id));
    }
}
