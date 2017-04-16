import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager , JhiLanguageService , AlertService } from 'ng-jhipster';

import { IntegrationModule } from './integration-module.model';
import { IntegrationModuleService } from './integration-module.service';

@Component({
    selector: 'jhi-integration-module-detail',
    templateUrl: './integration-module-detail.component.html'
})
export class IntegrationModuleDetailComponent implements OnInit, OnDestroy {

    integrationModule: IntegrationModule;
    private subscription: any;
    private eventSubscriber: Subscription;
    isSaving: boolean;

    constructor(
        private eventManager: EventManager,
        private jhiLanguageService: JhiLanguageService,
        private integrationModuleService: IntegrationModuleService,
        private route: ActivatedRoute,
        private alertService: AlertService
    ) {
        this.jhiLanguageService.setLocations(['integrationModule']);
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInIntegrationModules();
    }

    load(id) {
        this.integrationModuleService.find(id).subscribe((integrationModule) => {
            this.integrationModule = integrationModule;
        });
    }
    previousState() {
        window.history.back();
    }
    save() {
        this.isSaving = true;
        if (this.integrationModule.id !== undefined) {
            this.integrationModuleService.update(this.integrationModule)
                .subscribe((res: IntegrationModule) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        } else {
            this.integrationModuleService.create(this.integrationModule)
                .subscribe((res: IntegrationModule) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        }
    }

    private onSaveSuccess(result: IntegrationModule) {
        this.eventManager.broadcast({ name: 'integrationModuleModification', content: 'OK'});
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

    registerChangeInIntegrationModules() {
        this.eventSubscriber = this.eventManager.subscribe('integrationModuleListModification', (response) => this.load(this.integrationModule.id));
    }
}
