import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager , JhiLanguageService , AlertService } from 'ng-jhipster';

import { GeoZoneDescription } from './geo-zone-description.model';
import { GeoZoneDescriptionService } from './geo-zone-description.service';

@Component({
    selector: 'jhi-geo-zone-description-detail',
    templateUrl: './geo-zone-description-detail.component.html'
})
export class GeoZoneDescriptionDetailComponent implements OnInit, OnDestroy {

    geoZoneDescription: GeoZoneDescription;
    private subscription: any;
    private eventSubscriber: Subscription;
    isSaving: boolean;

    constructor(
        private eventManager: EventManager,
        private jhiLanguageService: JhiLanguageService,
        private geoZoneDescriptionService: GeoZoneDescriptionService,
        private route: ActivatedRoute,
        private alertService: AlertService
    ) {
        this.jhiLanguageService.setLocations(['geoZoneDescription']);
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInGeoZoneDescriptions();
    }

    load(id) {
        this.geoZoneDescriptionService.find(id).subscribe((geoZoneDescription) => {
            this.geoZoneDescription = geoZoneDescription;
        });
    }
    previousState() {
        window.history.back();
    }
    save() {
        this.isSaving = true;
        if (this.geoZoneDescription.id !== undefined) {
            this.geoZoneDescriptionService.update(this.geoZoneDescription)
                .subscribe((res: GeoZoneDescription) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        } else {
            this.geoZoneDescriptionService.create(this.geoZoneDescription)
                .subscribe((res: GeoZoneDescription) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        }
    }

    private onSaveSuccess(result: GeoZoneDescription) {
        this.eventManager.broadcast({ name: 'geoZoneDescriptionModification', content: 'OK'});
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

    registerChangeInGeoZoneDescriptions() {
        this.eventSubscriber = this.eventManager.subscribe('geoZoneDescriptionListModification', (response) => this.load(this.geoZoneDescription.id));
    }
}
