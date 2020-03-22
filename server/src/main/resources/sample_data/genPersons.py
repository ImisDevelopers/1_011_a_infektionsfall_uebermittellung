import string
from random import randint, randrange, choice, sample, getrandbits

last_names = ['Peters', 'Müller', 'Schulz', 'Schulze', 'Weber', 'Wagner', 'Richter', 'Klein', 'Bauer', 'Schröder',
              'Lange', 'Winkler', 'Winter']

male_first_names = ['Peter', 'Daniel', 'Hans', 'Franz', 'Karl', 'Tim', 'Jan', 'Jens', 'Kai']
female_fist_names = ['Jana', 'Lisa', 'Anna', 'Annika', 'Petra', 'Marie', 'Susanne', 'Daniela', 'Petra']

genders = ['männlich', 'weiblich']

email_providers = ['t-online', 'posteo', 'gmail', 'gmx', 'web']

streets = [
    "Aarhusweg",
    "Im Kramersfeld"
    "Aaröstraße",
    "Haidthöhe"
    "Aarweg",
    "Schernerweg",
    "Aastwiete",
    "Abbachstraße",
    "Niekampsweg",
    "Abbendiekshof",
    "Bückerheide",
    "Nordstraße",
    "Nordtstraße",
    "Nordufer"]

cities = ['Berlin', 'München', 'Hamburg', 'Köln', 'Düsseldorf', 'Kiel', 'Freiburg', 'Bochum', 'Frankfurt']

insurance_companies = ['AOK', 'Barmer', 'Techniker Krankenkasse', 'IKK Nord', 'KNAPPSCHAFT', 'DAK Gesundheit']

symptoms = ['Husten', 'Fieber', 'Schnupfen', 'Erkältung', 'Atemschwierigkeiten', 'Kopfschmerzen', 'Halschmerzen',
            'Gelenkschmerzen']


def gen_date_of_birth():
    year = 2020 - randint(15, 95)
    month = randrange(12) + 1
    day = randrange(28) + 1 if month == 2 else randrange(31) + 1
    return f'{year}-{month}-{day}'


def rand_num_str(len=10):
    return ''.join([str(randint(0, 10)) for _ in range(len)])


riscAreas = ['', 'GrandEst', 'Hubei', 'Tirol', 'Madrid', 'New York']

preIllnesses = ['', 'cancer', 'circulatory disorder', 'diabetes']


def insurance_number():
    upper_alphabet = string.ascii_uppercase
    random_letter = choice(upper_alphabet)
    number = randint(100000000, 999999999)
    return f'{random_letter}{number}'


def main():
    gender = choice(genders)
    firstName = choice(male_first_names) if gender == 'männlich' else choice(female_fist_names)

    person = {
        'lastName': choice(last_names),
        'firstName': firstName,
        'gender': gender,
        'dateOfBirth': gen_date_of_birth(),
        'email': f'{firstName[0]}@{choice(email_providers)}.de',

        'phoneNumber': rand_num_str(),
        'street': choice(streets),
        'houseNumber': randint(0, 100),
        'zip': rand_num_str(7),
        'city': choice(cities),
        'insuranceCompany': choice(insurance_companies),
        'insuranceMembershipNumber': insurance_number(),
        'fluImmunization': choice(['Ja', 'Nein']),
        'speedOfSymptomsOutbreak': choice(['Langsam', 'Mittel', 'Schnell']),
        'symptoms': sample(symptoms, randint(0, len(symptoms))),
        'coronaContacts': choice(['Ja', 'Nein']),

        'riskAreas': [choice(riscAreas)],
        'weakenedImmuneSystem': bool(getrandbits(1)),
        'preIllnesses': [choice(preIllnesses)]

    }

    if __name__ == '__main__':
        main()
