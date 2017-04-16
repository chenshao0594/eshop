import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager , JhiLanguageService , AlertService } from 'ng-jhipster';

import { Optin } from './optin.model';
import { OptinService } from './optin.service';

@Component({
    selector: 'jhi-optin-detail',
    templateUrl: './optin-detail.component.html'
})
export class OptinDetailComponent implements OnInit, OnDestroy {

    optin: Optin;
    private subscription: any;
    private eventSubscriber: Subscription;
    isSaving: boolean;

    constructor(
        private eventManager: EventManager,
        private jhiLanguageService: JhiLanguageService,
        private optinService: OptinService,
        private route: ActivatedRoute,
        private alertService: AlertService
    ) {
        this.jhiLanguageService.setLocations(['optin']);
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInOptins();
    }

    load(id) {
        this.optinService.find(id).subscribe((optin) => {
            this.optin = optin;
        });
    }
    previousState() {
        window.history.back();
    }
    save() {
        this.isSaving = true;
        if (this.optin.id !== undefined) {
            this.optinService.update(this.optin)
                .subscribe((res: Optin) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        } else {
            this.optinService.create(this.optin)
                .subscribe((res: Optin) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        }
    }

    private onSaveSuccess(result: Optin) {
        this.eventManager.broadcast({ name: 'optinModification', content: 'OK'});
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

    registerChangeInOptins() {
        this.eventSubscriber = this.eventManager.subscribe('optinListModification', (response) => this.load(this.optin.id));
    }
}
