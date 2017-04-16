import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager , JhiLanguageService , AlertService } from 'ng-jhipster';

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
    isSaving: boolean;

    constructor(
        private eventManager: EventManager,
        private jhiLanguageService: JhiLanguageService,
        private zoneService: ZoneService,
        private route: ActivatedRoute,
        private alertService: AlertService
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
    save() {
        this.isSaving = true;
        if (this.zone.id !== undefined) {
            this.zoneService.update(this.zone)
                .subscribe((res: Zone) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        } else {
            this.zoneService.create(this.zone)
                .subscribe((res: Zone) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        }
    }

    private onSaveSuccess(result: Zone) {
        this.eventManager.broadcast({ name: 'zoneModification', content: 'OK'});
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

    registerChangeInZones() {
        this.eventSubscriber = this.eventManager.subscribe('zoneListModification', (response) => this.load(this.zone.id));
    }
}
