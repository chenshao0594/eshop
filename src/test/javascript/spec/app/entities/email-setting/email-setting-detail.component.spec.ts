import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils, EventManager } from 'ng-jhipster';
import { EshopTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { EmailSettingDetailComponent } from '../../../../../../main/webapp/app/entities/email-setting/email-setting-detail.component';
import { EmailSettingService } from '../../../../../../main/webapp/app/entities/email-setting/email-setting.service';
import { EmailSetting } from '../../../../../../main/webapp/app/entities/email-setting/email-setting.model';

describe('Component Tests', () => {

    describe('EmailSetting Management Detail Component', () => {
        let comp: EmailSettingDetailComponent;
        let fixture: ComponentFixture<EmailSettingDetailComponent>;
        let service: EmailSettingService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [EshopTestModule],
                declarations: [EmailSettingDetailComponent],
                providers: [
                    DateUtils,
                    DataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    EmailSettingService,
                    EventManager
                ]
            }).overrideComponent(EmailSettingDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(EmailSettingDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(EmailSettingService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new EmailSetting(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.emailSetting).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
