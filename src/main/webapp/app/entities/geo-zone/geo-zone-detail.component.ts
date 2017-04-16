import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager , JhiLanguageService , AlertService } from 'ng-jhipster';

import { GeoZone } from './geo-zone.model';
import { GeoZoneService } from './geo-zone.service';

@Component({
    selector: 'jhi-geo-zone-detail',
    templateUrl: './geo-zone-detail.component.html'
})
export class GeoZoneDetailComponent implements OnInit, OnDestroy {

    geoZone: GeoZone;
    private subscription: any;
    private eventSubscriber: Subscription;
    isSaving: boolean;

    constructor(
        private eventManager: EventManager,
        private jhiLanguageService: JhiLanguageService,
        private geoZoneService: GeoZoneService,
        private route: ActivatedRoute,
        private alertService: AlertService
    ) {
        this.jhiLanguageService.setLocations(['geoZone']);
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInGeoZones();
    }

    load(id) {
        this.geoZoneService.find(id).subscribe((geoZone) => {
            this.geoZone = geoZone;
        });
    }
    previousState() {
        window.history.back();
    }
    save() {
        this.isSaving = true;
        if (this.geoZone.id !== undefined) {
            this.geoZoneService.update(this.geoZone)
                .subscribe((res: GeoZone) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        } else {
            this.geoZoneService.create(this.geoZone)
                .subscribe((res: GeoZone) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        }
    }

    private onSaveSuccess(result: GeoZone) {
        this.eventManager.broadcast({ name: 'geoZoneModification', content: 'OK'});
        this.isSaving = false;
    }

    private onSaveError(error) {
        try {
            error.json();
        } catch (exception) {
            error.message = error.text();
        }
        this.isSaving = false;
        this.onError(error);
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInGeoZones() {
        this.eventSubscriber = this.eventManager.subscribe('geoZoneListModification', (response) => this.load(this.geoZone.id));
    }
}
