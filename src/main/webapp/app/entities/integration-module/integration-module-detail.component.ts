import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager , JhiLanguageService  } from 'ng-jhipster';

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

    constructor(
        private eventManager: EventManager,
        private jhiLanguageService: JhiLanguageService,
        private integrationModuleService: IntegrationModuleService,
        private route: ActivatedRoute
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

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInIntegrationModules() {
        this.eventSubscriber = this.eventManager.subscribe('integrationModuleListModification', (response) => this.load(this.integrationModule.id));
    }
}
